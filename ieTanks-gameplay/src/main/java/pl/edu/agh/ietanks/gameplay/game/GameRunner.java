package pl.edu.agh.ietanks.gameplay.game;

import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Engine;
import pl.edu.agh.ietanks.engine.api.EngineFactory;
import pl.edu.agh.ietanks.engine.api.GameConfig;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.RoundResults;
import pl.edu.agh.ietanks.gameplay.bot.BotExecutor;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.innerapi.GameLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


class GameRunner implements Runnable, Game {

    private final GameId gameId;
    private final BoardDefinition gameBoard;
    private final List<BotAlgorithm> bots;
    private final EngineFactory gameEngineFactory;
    private final GameHistory historyStorage;
    private final List<Event> gameEvents;
    private GameLogger LOGGER = new StandardOutputGameLogger();
    private Engine gameEngine;

    public GameRunner(GameHistory historyStorage,
                      EngineFactory gameEngineFactory,
                      BoardDefinition gameBoard,
                      List<BotAlgorithm> gameBots) {
        this.gameId = new GameId(UUID.randomUUID().toString());
        this.historyStorage = historyStorage;
        this.gameEngineFactory = gameEngineFactory;
        this.gameEvents = new ArrayList<>();
        this.gameBoard = gameBoard;
        this.bots = gameBots;
    }

    private void setupEngineParams() {
        gameEngine = gameEngineFactory.createEngineInstance(gameBoard,
                Lists.transform(bots, botAlgorithm -> new BotExecutor(botAlgorithm.id(), botAlgorithm.pythonCode())),
                GameConfig.newBuilder().withTurnsLimit(10).createGameConfig());

        LOGGER.startGame();
    }

    @Override
    public void run() {
        setupEngineParams();

        RoundResults rResults;
        while ((rResults = gameEngine.nextMove()) != null && !rResults.isGameFinished()) {
            List<Event> roundEvents = rResults.getRoundEvents();
            gameEvents.addAll(roundEvents);

            LOGGER.nextRoundResults(rResults, gameEngine.currentBoard());
        }

        historyStorage.storeFinishedGame(this);
    }

    @Override
    public GameId getId() {
        return gameId;
    }

    @Override
    public List<Event> getGameEvents() {
        return Collections.unmodifiableList(gameEvents);
    }

    @Override
    public List<BotAlgorithm> getGameParticipants() {
        return Collections.unmodifiableList(bots);
    }
}
