package pl.edu.agh.ietanks.engine.simple.actions;

import pl.edu.agh.ietanks.engine.api.Action;
import pl.edu.agh.ietanks.engine.api.Direction;

/**
 * Represents move request from the bot to the game engine.
 */
public class Move implements Action {

    private Direction direction;
    private int step;

    public Move(Direction direction, int step) {
        super();
        this.direction = direction;
        this.step = step;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (step != move.step) return false;
        if (direction != move.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = direction.hashCode();
        result = 31 * result + step;
        return result;
    }
}
