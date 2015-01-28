package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.RoundResults;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * It's NOT thread safe. Do not share the same instance of SimpleEngine between different threads.
 */
public class SimpleEngine implements Engine {
    private final TanksOrderPolicy orderPolicy = new RandomOrderPolicy();

    private GameLogic gameLogic;
    private GameFinishingPolicy finishingPolicy;

    private final Queue<Bot> turns = new ArrayDeque<>();

    SimpleEngine(BoardDefinition initialBoard, List<? extends Bot> bots, GameConfig configuration) {
        final List<String> botIds = bots.stream().map(Bot::id).collect(Collectors.toList());
        this.gameLogic = new GameLogic(initialBoard, botIds);

        finishingPolicy = new CompositeFinishingPolicy(
                new MaximumNumberOfTurns(configuration.turnsLimit()),
                new LastBotLeft());

        turns.addAll(orderPolicy.determineTurnsOrder(bots));
    }

    @Override
    public RoundResults nextMove() {
        Bot currentBot = turns.poll();

        List<Event> missileEvents = gameLogic.moveMissiles();

        Action proposedAction = currentBot.performAction(gameLogic.board());
        List<Event> tankEvents = gameLogic.tryApplyAction(proposedAction, currentBot.id());

        turns.add(currentBot);
        final List<Event> turnEvents = Lists.newArrayList(Iterables.concat(missileEvents, tankEvents));

        finishingPolicy.nextTurn(gameLogic.board(), turnEvents);

        if (finishingPolicy.hasFinished()) {
            return RoundResults.Finished(turnEvents);
        } else {
            return RoundResults.Continue(turnEvents);
        }
    }

    @Override
    public GameplayBoardView currentBoard() {
        return gameLogic.board();
    }
}
