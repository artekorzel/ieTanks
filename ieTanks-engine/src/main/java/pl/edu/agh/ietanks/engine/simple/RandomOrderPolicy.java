package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.Bot;

import java.util.Collections;
import java.util.List;

public class RandomOrderPolicy implements TanksOrderPolicy {
    @Override
    public List<? extends Bot> determineTurnsOrder(List<? extends Bot> bots) {
        List<? extends Bot> orderedBots = Lists.newArrayList(bots);
        Collections.shuffle(orderedBots);
        return orderedBots;
    }
}
