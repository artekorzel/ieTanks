package pl.edu.agh.ietanks.engine.api.events;

/**
 * Indicates that a tank has been destroyed.
 */
public class TankDestroyed implements Event {

    private final int tankId;

    public TankDestroyed(int tankId) {
        super();
        this.tankId = tankId;
    }

    public int tankId() {
        return tankId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + tankId;
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
        TankDestroyed other = (TankDestroyed) obj;
        if (tankId != other.tankId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TankDestroyed {tankId=" + tankId + "}";
    }

}
