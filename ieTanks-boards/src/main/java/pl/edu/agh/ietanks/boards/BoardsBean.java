package pl.edu.agh.ietanks.boards;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.boards.model.Field;

import java.util.*;

@Service
public class BoardsBean implements BoardsReader {
    private Map<Integer, Board> boards = new HashMap<>();

    public BoardsBean() {
        boards.put(1, createSimpleBoard(1));
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards.values());
    }

    @Override
    public Board getBoard(int boardId) {
        return boards.get(boardId);
    }

    private Board createSimpleBoard(int id) {
        int width = 10;
        int height = 10;
        List<Field> obstacles = Arrays.asList(
                new Field(2, 0),
                new Field(2, 1),
                new Field(2, 2),
                new Field(1, 2),

                new Field(0, 5),
                new Field(0, 6),
                new Field(1, 6),
                new Field(2, 6),

                new Field(6, 2),
                new Field(7, 2),
                new Field(6, 3),
                new Field(6, 4),
                new Field(5, 4),
                new Field(5, 3),
                new Field(6, 5),

                new Field(0, 5),
                new Field(0, 6),
                new Field(1, 6),
                new Field(2, 6),

                new Field(5, 7),
                new Field(6, 7),
                new Field(7, 7),
                new Field(7, 8),
                new Field(7, 9),

                new Field(9, 0),
                new Field(9, 5)
        );
        List<Field> startingPoints = Arrays.asList(
                new Field(0, 0),
                new Field(0, 7),
                new Field(5, 3),
                new Field(9, 9)
        );
        return new Board(id, "Simple board", width, height, obstacles, startingPoints);
    }
}
