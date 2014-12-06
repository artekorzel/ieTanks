package pl.edu.agh.ietanks.engine.api;

public class Missile {

    private int missileId;
    private int speed;
    private Position position;
    private final Direction direction;

    public Missile(int missileId, int speed, Direction direction, Position position) {
        this.missileId = missileId;
        this.speed = speed;
        this.direction = direction;
        this.position = position;
    }

    public int id() {
        return missileId;
    }

    public int speed() {
        return speed;
    }

    public Direction direction() {
        return direction;
    }

    public Position position() {
        return position;
    }

    public void changePosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Missile missile = (Missile) o;

        if (missileId != missile.missileId) return false;
        if (speed != missile.speed) return false;
        if (direction != missile.direction) return false;
        if (position != null ? !position.equals(missile.position) : missile.position != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = missileId;
        result = 31 * result + speed;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Missile{" +
                "missileId=" + missileId +
                ", speed=" + speed +
                ", position=" + position +
                ", direction=" + direction +
                '}';
    }
}
