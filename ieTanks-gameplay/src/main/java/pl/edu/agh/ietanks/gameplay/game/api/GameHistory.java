package pl.edu.agh.ietanks.gameplay.game.api;

import java.util.List;
import java.util.Optional;

public interface GameHistory {
    public List<GameId> getFinishedGamesIds();
    public void storeFinishedGame(Game game);
    public Optional<Game> getGame(GameId gameId);
}
