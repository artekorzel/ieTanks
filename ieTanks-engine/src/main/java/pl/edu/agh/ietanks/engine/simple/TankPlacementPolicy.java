package pl.edu.agh.ietanks.engine.simple;

import pl.edu.agh.ietanks.engine.api.Position;

import java.util.List;
import java.util.Map;

public interface TankPlacementPolicy {
    Map<String, Position> place(List<String> botIds, List<Position> placements);
}
