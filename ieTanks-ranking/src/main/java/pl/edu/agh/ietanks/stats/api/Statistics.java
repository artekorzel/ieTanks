package pl.edu.agh.ietanks.stats.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Statistics {

    private StatId statId;
    private Map<Integer, TankStatistics> tankStats;

    public Statistics() {
        this(new HashMap<>());
    }

    public Statistics(Map<Integer, TankStatistics> tankStats) {
        this.tankStats = tankStats;
    }

    public StatId getStatId() {
        return statId;
    }

    public void setStatId(StatId statId) {
        this.statId = statId;
    }

    public Map<Integer, TankStatistics> getTankStatistics() {
        return Collections.unmodifiableMap(tankStats);
    }
}
