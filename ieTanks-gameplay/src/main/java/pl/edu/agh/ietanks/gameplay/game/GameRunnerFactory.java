package pl.edu.agh.ietanks.gameplay.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.bot.api.BotAlgorithm;
import pl.edu.agh.ietanks.engine.api.EngineFactory;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;

import java.util.List;

@Service
public class GameRunnerFactory {
    private final GameHistory storage;
    private final EngineFactory engineFactory;

    @Autowired
    public GameRunnerFactory(GameHistory storage, EngineFactory engineFactory) {
        this.storage = storage;
        this.engineFactory = engineFactory;
    }

    public GameRunner create(Board board, List<BotAlgorithm> algorithms) {
        GameRunner gameRunner = new GameRunner(storage, engineFactory, board, algorithms);
        return gameRunner;
    }
}
