package pl.edu.agh.ietanks.gameplay.game.rest;


import pl.edu.agh.ietanks.engine.api.events.AbstractMissileEvent;

public class MissileEventPojo {
    private final String action;
    private final int missileId;
    private final String tankId;
    private final int x;
    private final int y;
    private final int dirX;
    private final int dirY;
    private final int speed;

    public MissileEventPojo(AbstractMissileEvent missileEvent) {
        this.action = missileEvent.getAction().getActionName();
        this.missileId = missileEvent.id();
        this.tankId = missileEvent.tankId();
        this.x = missileEvent.position().fromLeft();
        this.y = missileEvent.position().fromTop();
        this.dirX = missileEvent.direction().getX();
        this.dirY = missileEvent.direction().getY();
        this.speed = missileEvent.speed();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getMissileId() {
        return missileId;
    }

    public String getAction() {
        return action;
    }

    public String getTankId() {
        return tankId;
    }

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public int getSpeed() {
        return speed;
    }
}
