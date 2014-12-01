package pl.edu.agh.ietanks.engine.testutils;

import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.Map;

public class FromStringBoardDefinition implements BoardDefinition {
    private final int width;
    private final int height;
    private final Map<String, Position> tanks;

    public FromStringBoardDefinition(int width, int height, Map<String, Position> tanks) {
        this.width = width;
        this.height = height;
        this.tanks = tanks;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public Map<String, Position> initialTankPositions() {
        return tanks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FromStringBoardDefinition that = (FromStringBoardDefinition) o;

        if (height != that.height) return false;
        if (width != that.width) return false;
        if (tanks != null ? !tanks.equals(that.tanks) : that.tanks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + (tanks != null ? tanks.hashCode() : 0);
        return result;
    }
}
