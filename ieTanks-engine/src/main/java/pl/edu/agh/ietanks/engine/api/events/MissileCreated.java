package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;

/**
 * Indicates that a tank created a missile.
 */
public class MissileCreated extends AbstractMissileEvent {


    public MissileCreated(int id, String tankId, Position position, Direction direction, int speed) {
        super(MissileAction.CREATED, id, tankId, position, direction, speed);
    }


    @Override
    public String toString() {
        return "MissileCreated {id=" + id +
                ", tankId=" + tankId +
                ", direction=" + direction + ", speed=" + speed
                + ", position=" + position + "}";
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tankId != null ? tankId.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + speed;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MissileCreated that = (MissileCreated) o;

        if (id != that.id) return false;
        if (tankId != null ? !tankId.equals(that.tankId) : that.tankId != null) return false;
        if (speed != that.speed) return false;
        if (direction != that.direction) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;

        return true;
    }


}
