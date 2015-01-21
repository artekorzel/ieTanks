package pl.edu.agh.ietanks.gameplay.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.bot.api.BotAlgorithm;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.bot.api.BotService;
import pl.edu.agh.ietanks.engine.util.LogExceptionRunnable;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SimpleGamePlay implements GamePlay {
    private static final int THREADS_IN_POOL = 5;

    private final ExecutorService executionService;

    @Autowired
    private GameRunnerFactory gameRunnerFactory;

    @Autowired
    private BoardsReader boardsReader;

    @Autowired
    /*@Qualifier("httpBotService")*/
    private BotService botService;

    public SimpleGamePlay() {
        executionService = Executors.newFixedThreadPool(THREADS_IN_POOL);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                executionService.shutdown();
            }
        });
    }

    //@Override
    private GameId startGame(Board gameBoard, List<BotAlgorithm> bots) {
        final GameRunner gameRunner = gameRunnerFactory.create(gameBoard, bots);
        executionService.execute(new LogExceptionRunnable(gameRunner));
        return gameRunner.getId();
    }

    @Override
    public GameId startNewGameplay(int boardId, List<BotId> botIds) {
        Board board = boardsReader.getBoard(boardId);
        List<BotAlgorithm> bots = botService.fetch(botIds);

        return startGame(board, bots);
    }
}
