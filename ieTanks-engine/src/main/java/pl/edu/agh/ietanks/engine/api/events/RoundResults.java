package pl.edu.agh.ietanks.engine.api.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RoundResults {

    private final boolean gameFinished;
    private final List<Event> eventsList;

    public boolean isGameFinished() {
        return gameFinished;
    }

    public List<Event> getRoundEvents() {
        return Collections.unmodifiableList(eventsList);
    }

    private RoundResults(List<Event> roundEvents, boolean gameFinished) {
        this.eventsList = roundEvents;
        this.gameFinished = gameFinished;
    }

    public static RoundResults Finished() {
        return new RoundResults(new ArrayList<Event>(), true);
    }

    public static RoundResults Finished(List<Event> roundEvents) {
        return new RoundResults(roundEvents, true);
    }

    public static RoundResults Continue(List<Event> roundEvents) {
        return new RoundResults(roundEvents, false);
    }
}
