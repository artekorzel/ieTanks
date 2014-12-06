package pl.edu.agh.ietanks.engine.api;

import java.util.Collections;
import java.util.Map;

/**
 * Defines the board for the game. Provides basic, static info about the board.
 * Assumes that the board is a tiled rectangle with (width x height) fields.
 */
public class BoardDefinition {

    private final int width;
    private final int height;
    private final Map<String, Position> initialTankPositions;

    public BoardDefinition(int width, int height, Map<String, Position> initialTankPositions) {
        this.width = width;
        this.height = height;
        this.initialTankPositions = Collections.unmodifiableMap(initialTankPositions);
    }

    /**
     * Width of the board (unit: fields/tiles number).
     */
    public int width() {
        return width;
    }

    /**
     * Height of the board (unit: fields/tiles number).
     */
    public int height() {
        return height;
    }

    /**
     * Defines the tanks (players): number and their initial positions.
     */
    public Map<String, Position> initialTankPositions() {
        return initialTankPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardDefinition that = (BoardDefinition) o;

        if (height != that.height) return false;
        if (width != that.width) return false;
        if (initialTankPositions != null ? !initialTankPositions.equals(that.initialTankPositions) : that.initialTankPositions != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + (initialTankPositions != null ? initialTankPositions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BoardDefinition{" +
                "width=" + width +
                ", height=" + height +
                ", initialTankPositions=" + initialTankPositions +
                '}';
    }
}
