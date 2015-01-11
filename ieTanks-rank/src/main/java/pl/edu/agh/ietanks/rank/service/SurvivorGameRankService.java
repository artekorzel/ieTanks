package pl.edu.agh.ietanks.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankDestroyed;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.rank.api.GameRank;
import pl.edu.agh.ietanks.rank.api.GameRankService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SurvivorGameRankService implements GameRankService {

    @Autowired
    GameHistory gameHistory;

    @Override
    public Optional<GameRank> getRankForGame(GameId gameId) {
        Optional<Game> game = gameHistory.getGame(gameId);
        if (!game.isPresent()) {
            return Optional.empty();
        }

        List<String> tankIds = new LinkedList<>();
        List<List<Event>> gameEventsByRound = game.get().getGameEventsByRound();
        for (List<Event> gameEvents : gameEventsByRound) {
            for (Event event : gameEvents) {
                if (event instanceof TankDestroyed) {
                    TankDestroyed tankDestroyedEvent = (TankDestroyed) event;
                    tankIds.add(0, tankDestroyedEvent.tankId());
                }
            }
        }

        return Optional.of(new GameRank(tankIds));
    }
}
