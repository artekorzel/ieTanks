package pl.edu.agh.ietanks.engine.api;

/**
 * Game configuration object.
 *
 * Contains options like maximum number of turns per game.
 */
public class GameConfig {
    private int turnsLimit;

    public GameConfig(int turnsLimit) {
        this.turnsLimit = turnsLimit;
    }

    public static GameConfigBuilder newBuilder() {
        return new GameConfigBuilder();
    }

    public static GameConfig defaults() {
        return newBuilder().createGameConfig();
    }

    public int turnsLimit() {
        return turnsLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameConfig)) return false;

        GameConfig that = (GameConfig) o;

        if (turnsLimit != that.turnsLimit) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return turnsLimit;
    }

    @Override
    public String toString() {
        return "GameConfig{" +
                "turnsLimit=" + turnsLimit +
                '}';
    }
}
