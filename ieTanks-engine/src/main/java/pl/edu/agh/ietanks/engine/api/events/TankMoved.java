package pl.edu.agh.ietanks.engine.api.events;


import pl.edu.agh.ietanks.engine.api.Direction;

/**
 * Indicates that a tank has moved.
 */
public class TankMoved implements Event {
    private final int tankId;
    private final Direction direction;
    private final int step;

    public TankMoved(int tankId, Direction direction, int step) {
        this.tankId = tankId;
        this.direction = direction;
        this.step = step;
    }

    public int tankId() {
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
        return "TankMoved{" +
                "tankId=" + tankId +
                ", direction=" + direction +
                ", step=" + step +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + step;
        result = prime * result + tankId;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TankMoved)) return false;

        TankMoved tankMoved = (TankMoved) o;

        if (tankId != tankMoved.tankId) return false;
        if (direction != tankMoved.direction) return false;
        if (step != tankMoved.step) return false;

        return true;
    }

}
