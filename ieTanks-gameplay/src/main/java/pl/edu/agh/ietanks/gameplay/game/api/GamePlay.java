package pl.edu.agh.ietanks.gameplay.game.api;


import pl.edu.agh.ietanks.bot.api.BotId;

import java.util.List;
import java.util.function.Consumer;

public interface GamePlay {
    //public GameId startGame(Board gameBoard, List<BotAlgorithm> bots);

    public GameId startNewGameplay(int boardId, List<BotId> botIds, Consumer<GameId> onGameFinished);
}
