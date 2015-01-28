package pl.edu.agh.ietanks.engine.api;

public class GameConfigBuilder {
    private int turnsLimit = 10000;

    public GameConfigBuilder withTurnsLimit(int turnsLimit) {
        this.turnsLimit = turnsLimit;
        return this;
    }

    public GameConfig createGameConfig() {
        return new GameConfig(turnsLimit);
    }
}