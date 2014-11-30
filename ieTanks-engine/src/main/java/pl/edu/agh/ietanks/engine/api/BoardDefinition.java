package pl.edu.agh.ietanks.engine.api;

import java.util.Map;

/**
 * Defines the board for the game. Provides basic, static info about the board.
 * Assumes that the board is a tiled rectangle with (width x height) fields.
 */
public interface BoardDefinition {

    /**
     * Width of the board (unit: fields/tiles number).
     */
    int width();

    /**
     * Height of the board (unit: fields/tiles number).
     */
    int height();

    /**
     * Defines the tanks (players): number and their initial positions.
     */
    Map<Integer, Position> initialTankPositions();
}
