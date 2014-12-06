package pl.edu.agh.ietanks.gameplay.bot.api;

public final class Shot implements Action {

    private ShotDirection direction;

    public Shot(ShotDirection direction) {
        this.direction = direction;
    }

    public ShotDirection direction() {
        return direction;
    }

}
