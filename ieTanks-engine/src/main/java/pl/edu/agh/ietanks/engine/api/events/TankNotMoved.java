package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;

/**
 * Indicates that a tank tried to move but did not succeed .
 */
public class TankNotMoved implements Event {

    private final String tankId;
    private final Direction direction;
    private final int step;

    public TankNotMoved(String tankId, Direction direction, int step) {
        this.tankId = tankId;
        this.direction = direction;
        this.step = step;
    }

    public String tankId() {
        return tankId;
    }

    public Direction direction() {
        return direction;
    }

    public int step() {
        return step;
    }

    @Override
    public String toString() {
        return "TankNotMoved{" +
                "tankId=" + tankId +
                ", direction=" + direction +
                ", step=" + step +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TankNotMoved that = (TankNotMoved) o;

        if (step != that.step) return false;
        if (direction != that.direction) return false;
        if (tankId != null ? !tankId.equals(that.tankId) : that.tankId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tankId != null ? tankId.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + step;
        return result;
    }
}
