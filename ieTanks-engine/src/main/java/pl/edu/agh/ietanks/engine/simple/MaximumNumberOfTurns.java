package pl.edu.agh.ietanks.engine.simple;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.events.Event;

import java.util.List;

public class MaximumNumberOfTurns implements GameFinishingPolicy {
    private final int turnsLimit;
    private int turnCounter = 0;

    public MaximumNumberOfTurns(int turnsLimit) {
        this.turnsLimit = turnsLimit;
    }

    @Override
    public void nextTurn(GameplayBoardView board, List<Event> turnEvents) {
        turnCounter++;
    }

    @Override
    public boolean hasFinished() {
        return turnCounter >= turnsLimit;
    }
}
