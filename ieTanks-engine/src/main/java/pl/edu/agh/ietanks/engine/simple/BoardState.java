package pl.edu.agh.ietanks.engine.simple;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.Missile;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.*;

public class BoardState implements GameplayBoardView {
    final Set<Position> taken = new HashSet<>();
    final Map<Integer, Position> tanks;
    final List<Missile> missiles;

    private final int width;
    private final int height;

    public BoardState(BoardDefinition definition) {
        this.width = definition.width();
        this.height = definition.height();

        tanks = new HashMap<>(definition.initialTankPositions());
        missiles = new ArrayList<>();
        taken.addAll(tanks.values());
    }

    @Override
    public List<Integer> tankIds() {
        return Lists.newArrayList(tanks.keySet());
    }

    @Override
    public Optional<Position> findTank(int tankId) {
        return Optional.fromNullable(tanks.get(tankId));
    }

    @Override
    public Collection<Missile> findMissiles() {
        return Collections.unmodifiableCollection(missiles);
    }

    @Override
    public Integer findTank(Position position) {
        for (int tankId : tanks.keySet()) {
            if (tanks.get(tankId).equals(position)) {
                return tankId;
            }
        }
        return null;
    }

    @Override
    public Collection<Missile> findMissiles(Position position) {
        Collection<Missile> missilesHere = new ArrayList<>();
        for (Missile missile : missiles) {
            if (missile.position().equals(position)) {
                missilesHere.add(missile);
            }
        }
        return Collections.unmodifiableCollection(missilesHere);
    }

    @Override
    public boolean isWithin(Position position) {
        boolean withinHorizontal = position.fromLeft() >= 0 && position.fromLeft() < width;
        boolean withinVertical = position.fromTop() >= 0 && position.fromTop() < height;

        return withinHorizontal && withinVertical;
    }

    @Override
    public boolean isAccessibleForTank(Position position) {
        return !taken.contains(position);
    }

    public void replaceTank(int tankId, Position destination) {
        Preconditions.checkArgument(isAccessibleForTank(destination), "invalid destination");
        Preconditions.checkArgument(isWithin(destination), "invalid destination");

        taken.remove(tanks.get(tankId));
        tanks.put(tankId, destination);
        taken.add(destination);
    }

    public void removeTank(int tankId) {
        taken.remove(tanks.get(tankId));
        tanks.remove(tankId);
    }

    public void createMissile(Missile missile) {
        if (isWithin(missile.position())) {
            missiles.add(missile);
        }
    }

    public void replaceMissile(Missile missile, Position destination) {
        if (isWithin(destination)) {
            missile.changePosition(destination);
        } else {
            missiles.remove(missile);
        }
    }

    public void removeMissile(Missile missile) {
        missiles.remove(missile);
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append("Board{\n");

        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height; ++j) {
                if (taken.contains(new Position(i, j))) {
                    for (Map.Entry<Integer, Position> entry : tanks.entrySet()) {
                        if (entry.getValue().equals(new Position(i, j))) {
                            representation.append(entry.getKey());
                            break;
                        }
                    }
                } else {
                    representation.append('.');
                }
            }
            representation.append('\n');
        }

        return representation.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardState)) return false;

        BoardState board = (BoardState) o;

        if (height != board.height) return false;
        if (width != board.width) return false;
        if (!taken.containsAll(board.taken) || !board.taken.containsAll(taken)) return false;
        if (!tanks.entrySet().containsAll(board.tanks.entrySet()) || !board.tanks.entrySet().containsAll(tanks.entrySet()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taken.hashCode();
        result = 31 * result + (tanks.hashCode());
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

}
