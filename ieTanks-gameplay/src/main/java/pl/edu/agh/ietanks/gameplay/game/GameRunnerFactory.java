package pl.edu.agh.ietanks.gameplay.game;

import com.google.common.base.Preconditions;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Engine;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameRunnerFactory {

    private final GameHistory storage;

    public GameRunnerFactory(GameHistory storage) {
        this.storage = storage;
    }

    public GameRunner create(Board board, List<BotAlgorithm> algorithms, Engine gameEngine) {
        BoardDefinition boardDefinition = toBoardDefinition(board, algorithms);
        GameRunner gameRunner = new GameRunner(storage, gameEngine, boardDefinition, algorithms);

        return gameRunner;
    }

    private BoardDefinition toBoardDefinition(Board board, List<BotAlgorithm> algorithms) {
        Preconditions.checkArgument(algorithms.size() <= 5, "More than 5 tanks not supported yet.");
        Map<String, Position> tanks = new HashMap<>();
        int width = board.getWidth();
        int height = board.getHeight();

        List<Position> goodPositions = new ArrayList<>();
        goodPositions.add(Position.topLeft());
        goodPositions.add(Position.topLeft().toRight(width - 1).toDown(height - 1));
        goodPositions.add(Position.topLeft().toRight(width - 1));
        goodPositions.add(Position.topLeft().toDown(height - 1));
        goodPositions.add(Position.topLeft().toRight((width - 1) / 2).toDown((height - 1) / 2));

        int i = 0;
        for (BotAlgorithm algorithm : algorithms) {
            tanks.put(algorithm.id().id(), goodPositions.get(i));
            i++;
        }

        return new BoardDefinition(width, height, tanks);
    }
}
