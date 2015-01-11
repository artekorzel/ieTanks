package pl.edu.agh.ietanks.engine.api.events;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;

/**
 * Indicates that a tank has been destroyed.
 */
public class TankDestroyed extends AbstractTankEvent {

    public TankDestroyed(String tankId, Position position) {
        super(TankAction.DESTROYED, tankId, Direction.None, position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TankDestroyed that = (TankDestroyed) o;

        if (!position.equals(that.position)) return false;
        if (tankId != null ? !tankId.equals(that.tankId) : that.tankId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tankId != null ? tankId.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TankDestroyed {" +
                "tankId=" + tankId +
                ", position=" + position +
                "}";
    }

}
