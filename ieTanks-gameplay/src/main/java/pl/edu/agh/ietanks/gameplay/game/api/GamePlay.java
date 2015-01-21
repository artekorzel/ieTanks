package pl.edu.agh.ietanks.gameplay.game.api;


import pl.edu.agh.ietanks.bot.api.BotId;

import java.util.List;

public interface GamePlay {
    //public GameId startGame(Board gameBoard, List<BotAlgorithm> bots);

    public GameId startNewGameplay(int boardId, List<BotId> botIds);
}
