package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;

/**
 * Indicates that a tank tried to move out of the board.
 */
public class TankBumpedIntoWall extends AbstractTankEvent {

    private final int step;

    public TankBumpedIntoWall(String tankId, Direction direction, int step, Position position) {
        super(TankAction.BUMPED_INTO_WALL, tankId, direction, position);
        this.step = step;
    }

    public int step() {
        return step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TankBumpedIntoWall that = (TankBumpedIntoWall) o;

        if (step != that.step) return false;
        if (direction != that.direction) return false;
        if (!position.equals(that.position)) return false;
        if (tankId != null ? !tankId.equals(that.tankId) : that.tankId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tankId != null ? tankId.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + step;
        return result;
    }

    @Override
    public String toString() {
        return "TankBumpedIntoWall{" +
                "tankId=" + tankId +
                ", direction=" + direction +
                ", step=" + step +
                ", position=" + position +
                '}';
    }
}
