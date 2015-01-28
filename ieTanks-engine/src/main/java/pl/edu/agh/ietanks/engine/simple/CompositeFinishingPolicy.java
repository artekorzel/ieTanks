package pl.edu.agh.ietanks.engine.simple;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.events.Event;

import java.util.Arrays;
import java.util.List;

public class CompositeFinishingPolicy implements GameFinishingPolicy {
    private final GameFinishingPolicy[] policies;

    public CompositeFinishingPolicy(GameFinishingPolicy... policies) {
        this.policies = policies;
    }

    @Override
    public void nextTurn(GameplayBoardView board, List<Event> turnEvents) {
        Arrays.stream(policies).forEach(policy -> policy.nextTurn(board, turnEvents));
    }

    @Override
    public boolean hasFinished() {
        return Arrays.stream(policies).anyMatch(GameFinishingPolicy::hasFinished);
    }
}
