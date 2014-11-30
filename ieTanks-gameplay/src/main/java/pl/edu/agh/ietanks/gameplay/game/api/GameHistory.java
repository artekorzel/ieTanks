package pl.edu.agh.ietanks.gameplay.game.api;

import java.util.List;
import java.util.UUID;

public interface GameHistory {
    public List<UUID> getFinishedGamesIds();
    public void storeFinishedGame(Game game);
    public Game getGame(UUID gameId);
}
