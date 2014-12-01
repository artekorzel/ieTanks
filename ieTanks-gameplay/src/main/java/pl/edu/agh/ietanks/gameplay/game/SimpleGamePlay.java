package pl.edu.agh.ietanks.gameplay.game;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.engine.util.LogExceptionRunnable;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.gameplay.game.innerapi.GameHistoryStorage;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SimpleGamePlay implements GamePlay {
    private static final int THREADS_IN_POOL = 5;

    private final GameHistoryStorage historyStorage = new InMemoryGameHistory();

    private final ExecutorService executionService;

    public SimpleGamePlay() {
        executionService = Executors.newFixedThreadPool(THREADS_IN_POOL);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                executionService.shutdown();
            }
        });
    }

    @Override
    public UUID startGame(Board gameBoard, List<BotAlgorithm> bots) {
        final GameRunner gameRunner = new GameRunner(historyStorage, gameBoard, bots);
        executionService.execute(new LogExceptionRunnable(gameRunner));
        return gameRunner.getId();
    }
}
