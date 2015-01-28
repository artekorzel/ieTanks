package pl.edu.agh.ietanks.engine.api.events;

import java.util.Optional;

public class GameFinished implements Event {
    private final Optional<String> winner;

    public GameFinished(Optional<String> winner) {
        this.winner = winner;
    }

    public Optional<String> winner() {
        return winner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameFinished)) return false;

        GameFinished that = (GameFinished) o;

        if (!winner.equals(that.winner)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return winner.hashCode();
    }

    @Override
    public String toString() {
        return "GameFinished{" +
                "winner=" + winner +
                '}';
    }

    @Override
    public EventGroup getEventGroup() {
        return EventGroup.FINISH;
    }
}
