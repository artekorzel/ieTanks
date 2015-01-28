package pl.edu.agh.ietanks.ranking.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankDestroyed;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.ranking.api.TankRanking;
import pl.edu.agh.ietanks.ranking.internal.api.Ranking;
import pl.edu.agh.ietanks.ranking.internal.api.RankingCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SurvivorRankingCalculator implements RankingCalculator {

    @Override
    public Ranking calculateRankingForGame(Game game) {
        Set<String> participatingTanks = game.getInitialParticipantsPositions().keySet();
        int numberOfTanks = participatingTanks.size();
        int tankPosition = numberOfTanks;
        Map<Integer, TankRanking> tankRankings = new HashMap<>();
        for (List<Event> gameEvents : game.getGameEventsByRound()) {
            for (Event event : gameEvents) {
                if (event instanceof TankDestroyed) {
                    TankDestroyed tankDestroyedEvent = (TankDestroyed) event;
                    TankRanking ranking = createDestroyedTankRanking(tankDestroyedEvent, tankPosition, numberOfTanks);
                    tankRankings.put(tankPosition--, ranking);
                }
            }
        }
        return new Ranking(tankRankings);
    }

    private TankRanking createDestroyedTankRanking(TankDestroyed event, int tankPosition, int numberOfTanks) {
        String destroyedTankId = event.tankId();
        int points = calculateNumberOfPoints(tankPosition, numberOfTanks);
        return new TankRanking(destroyedTankId, points);
    }

    private int calculateNumberOfPoints(int tankPosition, int numberOfTanks) {
        return (int) (100.0 * (1.0 - (tankPosition - 1) / (double) numberOfTanks));
    }
}
