package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;

/**
 * Indicates that a missile has been destroyed.
 */
public class MissileDestroyed implements Event {

    private final int id;
    private final Direction direction;
    private final int speed;
    private final Position position;

    public MissileDestroyed(int id, Position position, Direction direction, int speed) {
        this.id = id;
        this.position = position;
        this.direction = direction;
        this.speed = speed;
    }

    public int id() {
        return id;
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
        return "MissileCreated {id=" + id +
                ", direction=" + direction + ", speed=" + speed
                + ", position=" + position + "}";
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + speed;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MissileDestroyed that = (MissileDestroyed) o;

        if (id != that.id) return false;
        if (speed != that.speed) return false;
        if (direction != that.direction) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }


}
