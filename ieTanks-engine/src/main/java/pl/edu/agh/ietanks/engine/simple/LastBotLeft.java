package pl.edu.agh.ietanks.engine.simple;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.GameFinished;

import java.util.List;
import java.util.Optional;

public class LastBotLeft implements GameFinishingPolicy {
    boolean isGoingOn = true;

    @Override
    public void nextTurn(GameplayBoardView board, List<Event> turnEvents) {
        if (board.tankIds().size() == 1) {
            isGoingOn = false;
            String lastTank = board.tankIds().iterator().next();
            turnEvents.add(new GameFinished(Optional.of(lastTank)));
        } else if (board.tankIds().size() < 1) {
            isGoingOn = false;
            turnEvents.add(new GameFinished(Optional.empty()));
        }
    }

    @Override
    public boolean hasFinished() {
        return !isGoingOn;
    }
}
