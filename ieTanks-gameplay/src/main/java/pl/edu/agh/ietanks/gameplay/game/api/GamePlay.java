package pl.edu.agh.ietanks.gameplay.game.api;


import pl.edu.agh.ietanks.boards.model.Board;

import java.util.List;

public interface GamePlay {
    public void startGame(Board gameBoard, List<BotAlgorithm> bots);
}
