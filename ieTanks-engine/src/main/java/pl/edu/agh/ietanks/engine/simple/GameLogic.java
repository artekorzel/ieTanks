package pl.edu.agh.ietanks.engine.simple;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.api.events.*;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.NoOperation;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;

import javax.print.attribute.standard.Destination;
import java.util.*;

public class GameLogic {
    private final BoardState board;
    private final TankPlacementPolicy placementPolicy = new RandomPlacementPolicy();

    private int lastMissileId = 0;

    GameLogic(BoardState board) {
        this.board = board;
    }

    public GameLogic(BoardDefinition initialBoard, List<String> botIds) {
        this(new BoardState(initialBoard));

        Map<String, Position> placements = placementPolicy.place(botIds, initialBoard.initialTankPositions());

        for (Map.Entry<String, Position> placement : placements.entrySet()) {
            board.placeTank(placement.getKey(), placement.getValue());
        }
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
                events.add(new MissileMoved(missile.id(), missile.tankId(), destination, missile.direction(), missile.speed()));
            } else {
                missilesNotWithinBoard.put(missile, destination);
            }

        }

        // then remove missiles not within board
        for (Missile missile : missilesNotWithinBoard.keySet()) {
            board.removeMissile(missile);
            events.add(new MissileDestroyed(missile.id(), missile.tankId(), missilesNotWithinBoard.get(missile), missile.direction(), missile.speed()));
        }
    }

    private void removeShotTanksAndMissiles(List<Event> events, Map<Position, List<Missile>> missilePositions) {
        for (Position position : missilePositions.keySet()) {
            if (board.findTank(position) != null) {

                for (Missile missile : missilePositions.get(position)) {
                    board.removeMissile(missile);
                    events.add(new MissileDestroyed(missile.id(), missile.tankId(), missile.position(), missile.direction(), missile.speed()));
                }

                missilePositions.remove(position);

                String tankId = board.findTank(position);
                board.removeTank(tankId);
                events.add(new TankDestroyed(tankId, position));

            }
        }
    }

    private void removeCollidedMissiles(List<Event> events, Map<Position, List<Missile>> missilePositions) {
        for (Position position : missilePositions.keySet()) {
            if (missilePositions.get(position).size() > 1) {
                for (Missile missile : missilePositions.get(position)) {
                    board.removeMissile(missile);
                    events.add(new MissileDestroyed(missile.id(), missile.tankId(), missile.position(), missile.direction(), missile.speed()));
                }
                missilePositions.remove(position);
            }
        }
    }


    public GameplayBoardView board() {
        return board;
    }

    public List<Event> tryApplyAction(Action proposedAction, String botId) {
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
            tryToShoot(events, botId, botPosition, shot);
        } else if (proposedAction instanceof NoOperation) {
            doNotMove(botId, events, botPosition);
        }

        return events;
    }

    private void tryToMove(String botId, List<Event> events, Optional<Position> botPosition, Move move) {
        int possibleStep = 0;
        boolean bumpedIntoWall = false;
        Optional<Position> possiblePosition = botPosition;
        Preconditions.checkNotNull(botPosition, "invalid movement");
        for (int i = 1; i <= move.getStep(); ++i) {
            Position destination = findMoveDestination(possiblePosition, new Move(move.getDirection(), 1));
            Preconditions.checkNotNull(destination, "invalid movement");
            if (!board.isWithin(destination)) {
                bumpedIntoWall = true;
                break;
            }
            if (!board.isAccessibleForTank(destination)) {
                break;
            }
            possiblePosition = Optional.of(destination);
            possibleStep++;
            Collection<Missile> missiles = board.findMissiles(possiblePosition.get());
            if (!missiles.isEmpty()) {
                break;
            }
        }

        Position destination = findMoveDestination(board.findTank(botId), new Move(move.getDirection(), possibleStep));

        if (possibleStep == 0) {
            events.add(new TankNotMoved(botId, move.getDirection(), move.getStep(), destination));
        } else {
            events.add(new TankMoved(botId, move.getDirection(), possibleStep, destination));
            board.replaceTank(botId, destination);
        }
        if (bumpedIntoWall) {
            events.add(new TankBumpedIntoWall(botId, move.getDirection(), move.getStep(), destination));
        }

        // check if there are missiles here
        Collection<Missile> missiles = board.findMissiles(possiblePosition.get());
        if (!missiles.isEmpty()) {
            board.removeTank(botId);
            events.add(new TankDestroyed(botId, possiblePosition.get()));
            for (Missile missile : missiles) {
                board.removeMissile(missile);
                events.add(new MissileDestroyed(missile.id(), missile.tankId(), missile.position(), missile.direction(), missile.speed()));
            }
        }
    }

    private void tryToShoot(List<Event> events, String botId, Optional<Position> botPosition, Shot shot) {

        events.add(new TankNotMoved(botId, Direction.None, 0, botPosition.get()));

        Position destination = findMissileDestination(shot.getDirection(), botPosition.get(), shot.getSpeed());

        // create missile
        int missileId = nextMissileId();
        Missile missile = new Missile(missileId, botId, shot.getSpeed(), shot.getDirection(), destination);
        board.createMissile(missile);
        events.add(new MissileCreated(missileId, botId, destination, shot.getDirection(), shot.getSpeed()));

        // if missile destination is not within board, destroy it
        if (!board.isWithin(destination)) {
            events.add(new MissileDestroyed(missileId, botId, destination, shot.getDirection(), shot.getSpeed()));
        }

        // remove missiles at the same positions
        if (board.findMissiles(destination).size() > 1) {

            Collection<Missile> missilesToDestroy = board.findMissiles(destination);
            for (Missile missileToDestroy : missilesToDestroy) {
                board.removeMissile(missileToDestroy);
                events.add(new MissileDestroyed(missileToDestroy.id(), missileToDestroy.tankId(),
                        missileToDestroy.position(), missileToDestroy.direction(), missileToDestroy.speed()));
            }
        }

        // remove tank at the same position
        if (board.findTank(destination) != null) {
            String tankId = board.findTank(destination);
            board.removeTank(tankId);
            events.add(new TankDestroyed(tankId, destination));

            board.removeMissile(missile);
            events.add(new MissileDestroyed(missile.id(), missile.tankId(), missile.position(), missile.direction(), missile.speed()));
        }
    }

    private void doNotMove(String botId, List<Event> events, Optional<Position> botPosition) {
        events.add(new TankNotMoved(botId, Direction.None, 0, botPosition.get()));
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

    private int nextMissileId() {
        return ++lastMissileId;
    }

}
