package pl.edu.agh.ietanks.gameplay.game;

import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.innerapi.GameHistoryStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryGameHistory implements GameHistory, GameHistoryStorage {

    private Map<UUID, Game> historyGames = new ConcurrentHashMap<UUID, Game>();

    @Override
    public List<UUID> getFinishedGamesIds() {
        List<UUID> finishedGameIds = new ArrayList<UUID>();
        finishedGameIds.addAll(historyGames.keySet());

        return finishedGameIds;
    }

    @Override
    public Game getGame(int gameId) {
        return historyGames.get(gameId);
    }

    @Override
    public void storeFinishedGame(Game game) {
        historyGames.put(game.getId(), game);
    }
}
