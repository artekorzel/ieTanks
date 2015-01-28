package pl.edu.agh.ietanks.engine.simple;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.events.Event;

import java.util.List;

public interface GameFinishingPolicy {
    void nextTurn(GameplayBoardView board, List<Event> turnEvents);

    boolean hasFinished();
}
