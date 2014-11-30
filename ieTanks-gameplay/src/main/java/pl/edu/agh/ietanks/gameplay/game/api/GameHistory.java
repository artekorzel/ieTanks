package pl.edu.agh.ietanks.gameplay.game.api;

import java.util.List;

public interface GameHistory {
    public List<Integer> getFinishedGamesIds();
    public Game getGame(int gameId);
}
