package pl.edu.agh.ietanks.gameplay.game;

import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.innerapi.GameHistoryStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryGameHistory implements GameHistory, GameHistoryStorage {

    private Map<Integer, Game> historyGames = new ConcurrentHashMap<Integer, Game>();
    private AtomicInteger gameIdCounter = new AtomicInteger(0);


    @Override
    public List<Integer> getFinishedGamesIds() {
        List<Integer> finishedGameIds = new ArrayList<Integer>();
        finishedGameIds.addAll(historyGames.keySet());

        return finishedGameIds;
    }

    @Override
    public Game getGame(int gameId) {
        return historyGames.get(gameId);
    }

    @Override
    public int storeFinishedGame(Game game) {
        int gameId = gameIdCounter.getAndIncrement();
        historyGames.put(gameId, game);
        return gameId;
    }
}
