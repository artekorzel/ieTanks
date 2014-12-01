package pl.edu.agh.ietanks.engine.api;

public class Position {
    private int fromTop;
    private int fromLeft;

    public Position(int fromTop, int fromLeft) {
        this.fromTop = fromTop;
        this.fromLeft = fromLeft;
    }

    public static Position topLeft() {
        return new Position(0, 0);
    }

    public Position toRight(int step) {
        return new Position(fromTop, fromLeft + step);
    }

    public Position toLeft(int step) {
        return new Position(fromTop, fromLeft - step);
    }

    public Position toUp(int step) {
        return new Position(fromTop - step, fromLeft);
    }

    public Position toDown(int step) {
        return new Position(fromTop + step, fromLeft);
    }

    public Position toUpLeft(int step) {
        return new Position(fromTop - step, fromLeft - step);
    }

    public Position toDownLeft(int step) {
        return new Position(fromTop + step, fromLeft - step);
    }

    public Position toUpRight(int step) {
        return new Position(fromTop - step, fromLeft + step);
    }

    public Position toDownRight(int step) {
        return new Position(fromTop + step, fromLeft + step);
    }

    public int fromLeft() {
        return fromLeft;
    }

    public int fromTop() {
        return fromTop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (fromLeft != position.fromLeft) return false;
        if (fromTop != position.fromTop) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromLeft;
        result = 31 * result + fromTop;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "fromLeft=" + fromLeft +
                ", fromTop=" + fromTop +
                '}';
    }
}
