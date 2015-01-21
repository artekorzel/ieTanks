package pl.edu.agh.ietanks.league.service;

import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;

import java.util.List;

public class GamePlayStub implements GamePlay {
    private final GameId gameId;

    public GamePlayStub(GameId gameId) {
        this.gameId = gameId;
    }

    @Override
    public GameId startNewGameplay(int boardId, List<BotId> botIds) {
        return gameId;
    }
}
