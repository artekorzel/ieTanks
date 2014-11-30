package pl.edu.agh.ietanks.engine.api;

public class Missile {

    private int speed;
    private final Direction direction;
    private Position position;

    public Missile(int speed, Direction direction, Position position) {
        this.speed = speed;
        this.direction = direction;
        this.position = position;
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
        Missile other = (Missile) obj;
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

    @Override
    public String toString() {
        return "Missile {speed=" + speed + ", direction=" + direction
                + ", position=" + position + "}";
    }

}
