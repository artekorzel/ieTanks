package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.RoundResults;

import java.util.*;

public class SimpleEngine implements Engine {
    private GameLogic gameLogic;
    private Map<Bot, Integer> botIds = new HashMap<>();
    private Queue<Bot> turns = new ArrayDeque<>();
    private int turnCounter = 0;

    // TODO: extract Configuration object
    int MAX_TURNS = 10;

    @Override
    public void setup(BoardDefinition initialBoard, List<? extends Bot> bots) {
        this.gameLogic = new GameLogic(initialBoard);

        int id = 0;
        for (Bot bot : bots) {
            turns.add(bot);
            botIds.put(bot, id++);
        }
    }

    @Override
    public RoundResults nextMove() {
        Bot currentBot = turns.poll();
        int botId = botIds.get(currentBot);

        List<Event> missileEvents = gameLogic.moveMissiles();

        Action proposedAction = currentBot.performAction(gameLogic.board());
        List<Event> tankEvents = gameLogic.tryApplyAction(proposedAction, botId);

        turns.add(currentBot);
        final List<Event> turnEvents = Lists.newArrayList(Iterables.concat(missileEvents, tankEvents));

        turnCounter++;
        if (turnCounter == MAX_TURNS) {
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
