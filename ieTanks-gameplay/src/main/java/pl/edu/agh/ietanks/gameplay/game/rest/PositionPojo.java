package pl.edu.agh.ietanks.gameplay.game.rest;

import pl.edu.agh.ietanks.engine.api.Position;

public class PositionPojo {
    private final int x;
    private final int y;

    public PositionPojo(Position position) {
        this.x = position.fromLeft();
        this.y = position.fromTop();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
