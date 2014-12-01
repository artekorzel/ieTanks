package pl.edu.agh.ietanks.gameplay.game.innerapi;


import pl.edu.agh.ietanks.gameplay.game.api.Game;

public interface GameHistoryStorage {
    public void storeFinishedGame(Game game);
}
