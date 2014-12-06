package pl.edu.agh.ietanks.gameplay.game.api;

import java.util.List;
import java.util.UUID;

public interface GameHistory {
    public List<GameId> getFinishedGamesIds();
    public void storeFinishedGame(Game game);
    public Game getGame(GameId gameId);
}
