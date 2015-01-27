package pl.edu.agh.ietanks.stats.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.MissileCreated;
import pl.edu.agh.ietanks.engine.api.events.TankDestroyed;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.stats.api.Statistics;
import pl.edu.agh.ietanks.stats.api.StatCalculator;
import pl.edu.agh.ietanks.stats.api.TankStatistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SurvivorStatsCalculator implements StatCalculator {

    @Override
    public Statistics calculateStatForGame(Game game) {
        Set<String> participatingTanks = game.getInitialParticipantsPositions().keySet();
        Map<Integer, TankStatistics> stats = new HashMap<>();
        int numberOfTanks = participatingTanks.size();
        int tankPosition = numberOfTanks;
        int bulletsShot = 0;
        List<List<Event>> gameEventsByRound = game.getGameEventsByRound();
        for (List<Event> gameEvents : gameEventsByRound) {
            for (Event event : gameEvents) {
                if (event instanceof MissileCreated) {
                    bulletsShot++;
                } else if (event instanceof TankDestroyed) {
                    TankDestroyed tankDestroyedEvent = (TankDestroyed) event;
                    String destroyedTankId = tankDestroyedEvent.tankId();
                    TankStatistics tankStatistics = new TankStatistics(destroyedTankId, 1, bulletsShot, game.getGameEventsByRound().size());
                    stats.put(tankPosition--, tankStatistics);
                }
            }
        }
        return new Statistics(stats);
    }
}
