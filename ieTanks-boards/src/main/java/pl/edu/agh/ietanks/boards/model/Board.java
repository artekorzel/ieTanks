package pl.edu.agh.ietanks.boards.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private final String name;
    private final List<Field> obstacles = new ArrayList<>();
    private final List<Field> startingPoints = new ArrayList<>();
    private int id;
    private int width;
    private int height;

    public Board(int id, String name, int width, int height, List<Field> obstacles, List<Field> startingPoints) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.obstacles.addAll(obstacles);
        this.startingPoints.addAll(startingPoints);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Field> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }

    /**
     * @return List of possible places where tanks are allowed to be placed when game starts.
     * <p/>
     * The order of places matters, if we have 3 tanks and 5 available starting places,
     * the first three from the list should be chosen.
     */
    public List<Field> getStartingPoints() {
        return Collections.unmodifiableList(startingPoints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        if (height != board.height) return false;
        if (id != board.id) return false;
        if (width != board.width) return false;
        if (!name.equals(board.name)) return false;
        if (!obstacles.equals(board.obstacles)) return false;
        if (!startingPoints.equals(board.startingPoints)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + obstacles.hashCode();
        result = 31 * result + startingPoints.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", obstacles=" + obstacles +
                ", startingPoints=" + startingPoints +
                '}';
    }
}
