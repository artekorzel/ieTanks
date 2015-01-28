package pl.edu.agh.ietanks.gameplay.game;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryGameHistory implements GameHistory {

    private Map<GameId, Game> historyGames = new ConcurrentHashMap<>();

    @Override
    public List<GameId> getFinishedGamesIds() {
        List<GameId> finishedGameIds = new ArrayList<>();
        finishedGameIds.addAll(historyGames.keySet());

        return finishedGameIds;
    }

    @Override
    public Optional<Game> getGame(GameId gameId) {
        return Optional.of(historyGames.get(gameId));
    }

    @Override
    public void storeFinishedGame(Game game) {
        historyGames.put(game.getId(), game);
    }
}
