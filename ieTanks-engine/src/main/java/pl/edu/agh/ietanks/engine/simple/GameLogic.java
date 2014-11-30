package pl.edu.agh.ietanks.engine.simple;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.api.events.*;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;

import java.util.*;

public class GameLogic {
    private BoardState board;

    public GameLogic(BoardDefinition initialBoard) {
        this.board = new BoardState(initialBoard);
    }

    public List<Event> moveMissiles() {
        List<Event> events = new ArrayList<>();

        changeMissilePositions(events);

        Map<Position, List<Missile>> missilePositions = new HashMap<>();
        for (Missile missile : board.findMissiles()) {
            if (!missilePositions.containsKey(missile.position())) {
                missilePositions.put(missile.position(), new ArrayList<>());
            }
            missilePositions.get(missile.position()).add(missile);
        }

        // remove missiles and tanks at the same position
        removeShotTanksAndMissiles(events, missilePositions);

        // remove missiles at the same positions
        removeCollidedMissiles(events, missilePositions);


        return events;
    }

    private void changeMissilePositions(List<Event> events) {
        // first move all missiles
        Map<Missile, Position> missilesNotWithinBoard = new HashMap<>();
        for (Missile missile : board.findMissiles()) {
            Position destination = findMissileDestination(missile.direction(), missile.position(), missile.speed());
            Preconditions.checkNotNull(destination, "invalid direction");
            if (board.isWithin(destination)) {
                board.replaceMissile(missile, destination);
                events.add(new MissileMoved(destination, missile.direction(), missile.speed()));
            } else {
                missilesNotWithinBoard.put(missile, destination);
            }

        }

        // then remove missiles not within board
        for (Missile missile : missilesNotWithinBoard.keySet()) {
            board.removeMissile(missile);
            events.add(new MissileDestroyed(missilesNotWithinBoard.get(missile), missile.direction(), missile.speed()));
        }
    }

    private void removeShotTanksAndMissiles(List<Event> events, Map<Position, List<Missile>> missilePositions) {
        for (Position position : missilePositions.keySet()) {
            if (board.findTank(position) != null) {

                for (Missile missile : missilePositions.get(position)) {
                    board.removeMissile(missile);
                    events.add(new MissileDestroyed(missile.position(), missile.direction(), missile.speed()));
                }

                missilePositions.remove(position);

                int tankId = board.findTank(position);
                board.removeTank(tankId);
                events.add(new TankDestroyed(tankId));

            }
        }
    }

    private void removeCollidedMissiles(List<Event> events, Map<Position, List<Missile>> missilePositions) {
        for (Position position : missilePositions.keySet()) {
            if (missilePositions.get(position).size() > 1) {
                for (Missile missile : missilePositions.get(position)) {
                    board.removeMissile(missile);
                    events.add(new MissileDestroyed(missile.position(), missile.direction(), missile.speed()));
                }
                missilePositions.remove(position);
            }
        }
    }


    public GameplayBoardView board() {
        return board;
    }

    public List<Event> tryApplyAction(Action proposedAction, int botId) {
        List<Event> events = new ArrayList<>();

        Optional<Position> botPosition = board.findTank(botId);

        if (!botPosition.isPresent()) {
            return events;
        }

        if (proposedAction instanceof Move) {
            Move move = (Move) proposedAction;
            tryToMove(botId, events, botPosition, move);
        } else if (proposedAction instanceof Shot) {
            Shot shot = (Shot) proposedAction;
            tryToShoot(events, botPosition, shot);
        }

        return events;
    }

    private void tryToMove(int botId, List<Event> events, Optional<Position> botPosition, Move move) {
        Position destination = findMoveDestination(botPosition, move);
        Preconditions.checkNotNull(destination, "invalid movement");
        if (!board.isWithin(destination)) {
            events.add(new TankOutOfBoard(botId, move.getDirection(), move.getStep()));
        }
        while (!board.isWithin(destination) && move.getStep() > 1) {
            move.setStep(move.getStep() - 1);
            destination = findMoveDestination(botPosition, move);
            Preconditions.checkNotNull(destination, "invalid movement");
        }
        if (board.isAccessibleForTank(destination)) {
            board.replaceTank(botId, destination);
            events.add(new TankMoved(botId, move.getDirection(), move.getStep()));
        } else {
            events.add(new TankNotMoved(botId, move.getDirection(), move.getStep()));
        }
        // check if there are missiles here
        Collection<Missile> missiles = board.findMissiles(destination);
        if (!missiles.isEmpty()) {
            board.removeTank(botId);
            events.add(new TankDestroyed(botId));
            for (Missile missile : missiles) {
                board.removeMissile(missile);
                events.add(new MissileDestroyed(missile.position(), missile.direction(), missile.speed()));
            }
        }
    }

    private void tryToShoot(List<Event> events, Optional<Position> botPosition, Shot shot) {
        Position destination = findMissileDestination(shot.getDirection(), botPosition.get(), shot.getSpeed());

        // create missile
        Missile missile = new Missile(shot.getSpeed(), shot.getDirection(), destination);
        board.createMissile(missile);
        events.add(new MissileCreated(destination, shot.getDirection(), shot.getSpeed()));

        // remove missiles at the same positions
        if (board.findMissiles(destination).size() > 1) {

            Collection<Missile> missilesToDestroy = board.findMissiles(destination);
            for (Missile missileToDestroy : missilesToDestroy) {
                board.removeMissile(missileToDestroy);
                events.add(new MissileDestroyed(
                        missileToDestroy.position(), missileToDestroy.direction(), missileToDestroy.speed()));
            }
        }

        // remove tank at the same position
        if (board.findTank(destination) != null) {
            int tankId = board.findTank(destination);
            board.removeTank(tankId);
            events.add(new TankDestroyed(tankId));

            board.removeMissile(missile);
            events.add(new MissileDestroyed(missile.position(), missile.direction(), missile.speed()));
        }
    }

    private Position findMissileDestination(Direction direction, Position position, int speed) {
        Position destination = null;
        if (direction == Direction.Right) {
            destination = position.toRight(speed);
        } else if (direction == Direction.Left) {
            destination = position.toLeft(speed);
        } else if (direction == Direction.Up) {
            destination = position.toUp(speed);
        } else if (direction == Direction.Down) {
            destination = position.toDown(speed);
        } else if (direction == Direction.Up_Left) {
            destination = position.toUpLeft(speed);
        } else if (direction == Direction.Up_Right) {
            destination = position.toUpRight(speed);
        } else if (direction == Direction.Down_Left) {
            destination = position.toDownLeft(speed);
        } else if (direction == Direction.Down_Right) {
            destination = position.toDownRight(speed);
        }
        return destination;
    }

    private Position findMoveDestination(Optional<Position> botPosition,
                                         Move move) {
        Position destination = null;

        if (move.getDirection() == Direction.Right) {
            destination = botPosition.get().toRight(move.getStep());
        } else if (move.getDirection() == Direction.Left) {
            destination = botPosition.get().toLeft(move.getStep());
        } else if (move.getDirection() == Direction.Up) {
            destination = botPosition.get().toUp(move.getStep());
        } else if (move.getDirection() == Direction.Down) {
            destination = botPosition.get().toDown(move.getStep());
        }
        return destination;
    }

}
