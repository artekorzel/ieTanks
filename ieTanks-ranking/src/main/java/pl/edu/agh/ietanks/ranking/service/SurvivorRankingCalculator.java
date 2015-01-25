package pl.edu.agh.ietanks.ranking.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankDestroyed;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.ranking.api.Ranking;
import pl.edu.agh.ietanks.ranking.api.RankingCalculator;
import pl.edu.agh.ietanks.ranking.api.TankRanking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SurvivorRankingCalculator implements RankingCalculator {

    @Override
    public Ranking calculateRankingForGame(Game game) {
        Set<String> participatingTanks = game.getInitialParticipantsPositions().keySet();
        Map<Integer, TankRanking> rank = new HashMap<>();
        int numberOfTanks = participatingTanks.size();
        int tankPosition = numberOfTanks;
        List<List<Event>> gameEventsByRound = game.getGameEventsByRound();
        for (List<Event> gameEvents : gameEventsByRound) {
            for (Event event : gameEvents) {
                if (event instanceof TankDestroyed) {
                    TankDestroyed tankDestroyedEvent = (TankDestroyed) event;
                    String destroyedTankId = tankDestroyedEvent.tankId();
                    int points = calculateNumberOfPoints(tankPosition, numberOfTanks);
                    TankRanking tankRanking = new TankRanking(destroyedTankId, points);
                    rank.put(tankPosition--, tankRanking);
                }
            }
        }
        return new Ranking(rank);
    }

    private int calculateNumberOfPoints(int tankPosition, int numberOfTanks) {
        return (int) (100.0 * (1.0 - (tankPosition - 1) / (double) numberOfTanks));
    }
}
