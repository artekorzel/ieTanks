package pl.edu.agh.ietanks.gameplay.game.api;

import java.util.List;
import java.util.UUID;

public interface GameHistory {
    public List<UUID> getFinishedGamesIds();

    public Game getGame(int gameId);
}
