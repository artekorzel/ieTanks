package pl.edu.agh.ietanks.engine.api;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Defines the board for the game. Provides basic, static info about the board.
 * Assumes that the board is a tiled rectangle with (width x height) fields.
 */
public class BoardDefinition {

    private final int width;
    private final int height;
    private final List<Position> initialTankPositions;

    public BoardDefinition(int width, int height, List<Position> initialTankPositions) {
        this.width = width;
        this.height = height;
        this.initialTankPositions = Collections.unmodifiableList(Lists.newArrayList(initialTankPositions));
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
     * Defines the places where tanks may start the game.
     */
    public List<Position> initialTankPositions() {
        return initialTankPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardDefinition)) return false;

        BoardDefinition that = (BoardDefinition) o;

        if (height != that.height) return false;
        if (width != that.width) return false;
        if (!initialTankPositions.equals(that.initialTankPositions)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + initialTankPositions.hashCode();
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
