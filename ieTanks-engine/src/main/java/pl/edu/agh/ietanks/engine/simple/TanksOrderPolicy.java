package pl.edu.agh.ietanks.engine.simple;

import pl.edu.agh.ietanks.engine.api.Bot;

import java.util.List;

public interface TanksOrderPolicy {
    List<? extends Bot> determineTurnsOrder(List<? extends Bot> bots);
}
