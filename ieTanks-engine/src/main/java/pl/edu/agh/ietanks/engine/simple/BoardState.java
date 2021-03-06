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
    final Map<String, Position> tanks;
    final List<Missile> missiles;

    private final int width;
    private final int height;

    public BoardState(BoardDefinition definition) {
        this.width = definition.width();
        this.height = definition.height();

        tanks = new HashMap<>();
        missiles = new ArrayList<>();
        taken.addAll(definition.obstacles());
    }

    @Override
    public List<String> tankIds() {
        return Lists.newArrayList(tanks.keySet());
    }

    @Override
    public Optional<Position> findTank(String tankId) {
        return Optional.fromNullable(tanks.get(tankId));
    }

    @Override
    public Collection<Missile> findMissiles() {
        return Collections.unmodifiableCollection(missiles);
    }

    @Override
    public String findTank(Position position) {
        for (String tankId : tanks.keySet()) {
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
        return !taken.contains(position) && isWithin(position);
    }

    public void replaceTank(String tankId, Position destination) {
        Preconditions.checkArgument(isAccessibleForTank(destination), "invalid destination");
        Preconditions.checkArgument(isWithin(destination), "invalid destination");

        taken.remove(tanks.get(tankId));
        tanks.put(tankId, destination);
        taken.add(destination);
    }

    public void placeTank(String tankId, Position place) {
        replaceTank(tankId, place);
    }

    public void removeTank(String tankId) {
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

        for (int fromTop = 0; fromTop < height; ++fromTop) {
            for (int fromLeft = 0; fromLeft < width; ++fromLeft) {
                if (taken.contains(new Position(fromTop, fromLeft))) {
                    for (Map.Entry<String, Position> entry : tanks.entrySet()) {
                        if (entry.getValue().equals(new Position(fromTop, fromLeft))) {
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
        if (o == null || getClass() != o.getClass()) return false;

        BoardState that = (BoardState) o;

        if (height != that.height) return false;
        if (width != that.width) return false;
        if (missiles != null ? !missiles.equals(that.missiles) : that.missiles != null) return false;
        if (taken != null ? !taken.equals(that.taken) : that.taken != null) return false;
        if (tanks != null ? !tanks.equals(that.tanks) : that.tanks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taken != null ? taken.hashCode() : 0;
        result = 31 * result + (tanks != null ? tanks.hashCode() : 0);
        result = 31 * result + (missiles != null ? missiles.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }
}
