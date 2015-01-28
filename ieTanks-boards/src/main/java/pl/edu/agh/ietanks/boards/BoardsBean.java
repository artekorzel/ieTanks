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
                new Field(1, 1),
                new Field(1, 8),
                new Field(8, 1),
                new Field(8, 8),
                new Field(3, 3),
                new Field(3, 6),
                new Field(6, 3),
                new Field(6, 6)
        );
        List<Field> startingPoints = Arrays.asList(
                new Field(0, 0),
                new Field(0, 9),
                new Field(9, 0),
                new Field(9, 9)
        );
        return new Board(id, "Simple board", width, height, obstacles, startingPoints);
    }
}
