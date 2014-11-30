package pl.edu.agh.ietanks.gameplay.game;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryGameHistory implements GameHistory {

    private Map<UUID, Game> historyGames = new ConcurrentHashMap<UUID, Game>();

    @Override
    public List<UUID> getFinishedGamesIds() {
        List<UUID> finishedGameIds = new ArrayList<UUID>();
        finishedGameIds.addAll(historyGames.keySet());

        return finishedGameIds;
    }

    @Override
    public Game getGame(UUID gameId) {
        return historyGames.get(gameId);
    }

    @Override
    public void storeFinishedGame(Game game) {
        historyGames.put(game.getId(), game);
    }
}
