package pl.edu.agh.ietanks.engine.api;

import com.google.common.base.Optional;

import java.util.Collection;
import java.util.List;

/**
 * Board view visible by bots during gameplay.
 */
public interface GameplayBoardView {
    /**
     * Returns all tanks (their identifiers) that are still on board.
     */
    List<String> tankIds();

    /**
     * Finds tank on map by its id.
     */
    Optional<Position> findTank(String id);

    /**
     * Finds tank on given position on map.
     */
    String findTank(Position position);

    /**
     * Finds all missiles.
     */
    Collection<Missile> findMissiles();

    /**
     * Finds missiles on given position on map.
     */
    Collection<Missile> findMissiles(Position position);

    /**
     * Checks whether the given position is within the board.
     */
    boolean isWithin(Position position);

    /**
     * Checks whether the tank may take a given position
     */
    boolean isAccessibleForTank(Position position);
}
