package pl.edu.agh.ietanks.boards.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

    private int id;
    private String name;
    private int width;
    private int height;
    private List<Obstacle> obstacles = new ArrayList<>();

    public Board(int id, String name, int width, int height, List<Obstacle> obstacles) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
        this.obstacles.addAll(obstacles);
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

    public List<Obstacle> getObstacles() {
        return Collections.unmodifiableList(obstacles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (height != board.height) return false;
        if (id != board.id) return false;
        if (width != board.width) return false;
        if (!name.equals(board.name)) return false;
        if (!obstacles.equals(board.obstacles)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + obstacles.hashCode();
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
                '}';
    }
}
