package pl.edu.agh.ietanks.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankDestroyed;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.rank.api.*;

import java.util.*;

@Service
public class SurvivorGameRankService implements GameRankService {

    @Autowired
    GameRankStorage gameRankStorage;

    @Autowired
    GameRanksMerger gameRanksMerger;

    @Override
    public Optional<GameRank> getRank(RankId id) {
        return gameRankStorage.get(id);
    }

    @Override
    public GameRank calculateRankForGames(List<Game> games) {
        GameRank gameRank = games.stream()
                .map(this::calculateRankForGameInternal)
                .reduce(new GameRank(), gameRanksMerger);
        gameRankStorage.store(gameRank);
        return gameRank;
    }

    @Override
    public GameRank calculateRankForGame(Game game) {
        GameRank gameRank = calculateRankForGameInternal(game);
        gameRankStorage.store(gameRank);
        return gameRank;
    }

    private GameRank calculateRankForGameInternal(Game game) {
        Set<String> participatingTanks = game.getInitialParticipantsPositions().keySet();
        Map<Integer, TankRank> rank = new HashMap<>();
        int numberOfTanks = participatingTanks.size();
        int tankPosition = numberOfTanks;
        List<List<Event>> gameEventsByRound = game.getGameEventsByRound();
        for (List<Event> gameEvents : gameEventsByRound) {
            for (Event event : gameEvents) {
                if (event instanceof TankDestroyed) {
                    TankDestroyed tankDestroyedEvent = (TankDestroyed) event;
                    String destroyedTankId = tankDestroyedEvent.tankId();
                    TankRank tankRank = new TankRank(destroyedTankId, calculateNumberOfPoints(tankPosition, numberOfTanks));
                    rank.put(tankPosition--, tankRank);
                }
            }
        }
        return new GameRank(rank);
    }

    private int calculateNumberOfPoints(int tankPosition, int numberOfTanks) {
        return (int) (100.0 * (1.0 - (tankPosition - 1) / (double) numberOfTanks));
    }
}
