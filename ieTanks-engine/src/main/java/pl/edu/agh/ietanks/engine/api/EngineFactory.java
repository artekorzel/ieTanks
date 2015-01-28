package pl.edu.agh.ietanks.engine.api;

import java.util.List;

/**
 * Creates instances of game engine.
 */
public interface EngineFactory {
    /**
     * Initializes the engine. Needs board and bots definition.
     *
     * @param initialBoard  a board model we are starting with
     * @param bots          list of bot definitions, capable of performing moves
     * @param configuration game configuration options
     */
    Engine createEngineInstance(BoardDefinition initialBoard, List<? extends Bot> bots, GameConfig configuration);
}
