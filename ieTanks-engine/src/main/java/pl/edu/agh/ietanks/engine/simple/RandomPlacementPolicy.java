package pl.edu.agh.ietanks.engine.simple;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RandomPlacementPolicy implements TankPlacementPolicy {

    @Override
    public Map<String, Position> place(List<String> botIds, List<Position> startingPositions) {
        Preconditions.checkArgument(botIds.size() <= startingPositions.size(), "More tanks than board supports!");

        final List<Position> selectedPositions = startingPositions.stream()
                .limit(botIds.size()).collect(Collectors.toList());

        final List<String> shuffledBots = Lists.newArrayList(botIds);
        Collections.shuffle(shuffledBots);

        Map<String, Position> placement = new HashMap<>();

        for (int i = 0; i < shuffledBots.size(); ++i) {
            placement.put(shuffledBots.get(i), selectedPositions.get(i));
        }

        return placement;
    }
}
