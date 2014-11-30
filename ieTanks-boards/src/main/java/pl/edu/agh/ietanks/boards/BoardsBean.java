package pl.edu.agh.ietanks.boards;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.boards.model.Obstacle;

import java.util.*;

@Service
public class BoardsBean implements BoardsReader {
    private Map<Integer, Board> boards = new HashMap<>();

    public BoardsBean() {
        Board board = new Board(1, "Test board", 10, 20, Arrays.asList(new Obstacle(5, 10)));
        boards.put(1, board);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards.values());
    }

    @Override
    public Board getBoard(int boardId) {
        return boards.get(boardId);
    }
}
