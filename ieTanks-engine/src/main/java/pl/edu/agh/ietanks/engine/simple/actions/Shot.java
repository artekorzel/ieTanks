package pl.edu.agh.ietanks.engine.simple.actions;

import pl.edu.agh.ietanks.engine.api.Action;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView.Direction;

/**
 * Represents shot request from the bot to the game engine.
 */
public class Shot implements Action {

    private GameplayBoardView.Direction direction;
    private int speed;

    public Shot(Direction direction, int speed) {
        super();
        this.direction = direction;
        this.speed = speed;
    }

    public GameplayBoardView.Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shot shot = (Shot) o;

        if (speed != shot.speed) return false;
        if (direction != shot.direction) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = direction.hashCode();
        result = 31 * result + speed;
        return result;
    }
}
