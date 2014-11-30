package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;

/**
 * Indicates that a tank tried to move out of the board.
 */
public class TankOutOfBoard implements Event {

    private final int tankId;
    private final Direction direction;
    private final int step;

    public TankOutOfBoard(int tankId, Direction direction, int step) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TankOutOfBoard that = (TankOutOfBoard) o;

        if (step != that.step) return false;
        if (tankId != that.tankId) return false;
        if (direction != that.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tankId;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + step;
        return result;
    }

    @Override
    public String toString() {
        return "TankOutOfBoard{" +
                "tankId=" + tankId +
                ", direction=" + direction +
                ", step=" + step +
                '}';
    }
}
