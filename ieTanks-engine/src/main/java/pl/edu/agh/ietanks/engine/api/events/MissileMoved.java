package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;

/**
 * Indicates that a missile has moved.
 */
public class MissileMoved implements Event {

    private final Direction direction;
    private final int speed;
    private final Position position;

    public MissileMoved(Position position, Direction direction, int speed) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
    }

    public Position position() {
        return position;
    }

    public Direction direction() {
        return direction;
    }

    public int speed() {
        return speed;
    }

    @Override
    public String toString() {
        return "MissileCreated {direction=" + direction + ", speed=" + speed
                + ", position=" + position + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result
                + ((position == null) ? 0 : position.hashCode());
        result = prime * result + speed;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MissileMoved other = (MissileMoved) obj;
        if (direction != other.direction)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (speed != other.speed)
            return false;
        return true;
    }

}
