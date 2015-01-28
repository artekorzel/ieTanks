package pl.edu.agh.ietanks.gameplay.bot.api;

public final class Move implements Action {

    private MoveDirection direction;

    public Move(MoveDirection direction) {
        this.direction = direction;
    }

    public MoveDirection direction() {
        return direction;
    }

}
