package pl.edu.agh.ietanks.gameplay.game.api;

public class BotId {
    private String id;

    public BotId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BotId botId = (BotId) o;

        if (id != null ? !id.equals(botId.id) : botId.id != null) return false;

        return true;
    }

    public String id() {
        return id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BotId{" +
                "id='" + id + '\'' +
                '}';
    }
}
