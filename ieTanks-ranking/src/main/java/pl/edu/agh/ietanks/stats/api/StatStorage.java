package pl.edu.agh.ietanks.stats.api;

import java.util.Optional;

public interface StatStorage {
    StatId createStat();

    void updateStat(Statistics statistics);

    Optional<Statistics> findStatById(StatId statId);
}
