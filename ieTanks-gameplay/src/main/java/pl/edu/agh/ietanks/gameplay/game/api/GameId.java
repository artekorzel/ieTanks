package pl.edu.agh.ietanks.gameplay.game.api;

public class GameId {

    private final String id;

    public GameId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameId gameId = (GameId) o;

        if (id != null ? !id.equals(gameId.id) : gameId.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GameId{" +
                "id='" + id + '\'' +
                '}';
    }
}
