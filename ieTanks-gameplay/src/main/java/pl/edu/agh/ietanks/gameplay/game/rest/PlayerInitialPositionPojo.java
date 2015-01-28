package pl.edu.agh.ietanks.gameplay.game.rest;

import pl.edu.agh.ietanks.engine.api.Position;

public class PlayerInitialPositionPojo {
    private final String playerId;
    private final PositionPojo position;

    public PlayerInitialPositionPojo(String playerId, Position position) {
        this.playerId = playerId;
        this.position = new PositionPojo(position);
    }

    public PositionPojo getPosition() {
        return position;
    }

    public String getPlayerId() {
        return playerId;
    }
}
