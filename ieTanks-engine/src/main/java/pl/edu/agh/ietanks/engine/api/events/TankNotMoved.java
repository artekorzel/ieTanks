package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;

public class TankNotMoved implements Event {

    private final int tankId;
    private final Direction direction;
    private final int step;

    public TankNotMoved(int tankId, Direction direction, int step) {
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
        return "TankNotMoved{" +
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
        if (!(o instanceof TankNotMoved)) return false;

        TankNotMoved tankNotMoved = (TankNotMoved) o;

        if (tankId != tankNotMoved.tankId) return false;
        if (direction != tankNotMoved.direction) return false;
        if (step != tankNotMoved.step) return false;

        return true;
    }
}
