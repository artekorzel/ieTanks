package pl.edu.agh.ietanks.gameplay.game.api;


import pl.edu.agh.ietanks.boards.model.Board;

import java.util.List;
import java.util.UUID;

public interface GamePlay {
    public GameId startGame(Board gameBoard, List<BotAlgorithm> bots);
}
