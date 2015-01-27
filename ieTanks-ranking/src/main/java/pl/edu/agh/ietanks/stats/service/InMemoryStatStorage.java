package pl.edu.agh.ietanks.stats.service;

import org.springframework.stereotype.Repository;
import pl.edu.agh.ietanks.stats.api.Statistics;
import pl.edu.agh.ietanks.stats.api.StatId;
import pl.edu.agh.ietanks.stats.api.StatStorage;
import pl.edu.agh.ietanks.stats.exceptions.StatNotFoundException;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryStatStorage implements StatStorage {

    private static final AtomicLong statIdBase = new AtomicLong();
    private static final Map<StatId, Statistics> gameStatsCache = new ConcurrentHashMap<>();

    @Override
    public StatId createStat() {
        StatId statId = new StatId("" + statIdBase.incrementAndGet());
        Statistics stat = new Statistics();
        stat.setStatId(statId);
        gameStatsCache.put(statId, stat);
        return statId;
    }

    @Override
    public void updateStat(Statistics stat) {
        StatId statId = stat.getStatId();
        if (gameStatsCache.containsKey(statId)) {
            gameStatsCache.put(statId, stat);
        }
        throw new StatNotFoundException(statId);
    }

    @Override
    public Optional<Statistics> findStatById(StatId statsId) {
        if (gameStatsCache.containsKey(statsId)) {
            return Optional.of(gameStatsCache.get(statsId));
        }
        return Optional.empty();
    }
}
