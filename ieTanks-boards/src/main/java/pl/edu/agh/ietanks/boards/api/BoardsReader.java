package pl.edu.agh.ietanks.boards.api;


import pl.edu.agh.ietanks.boards.model.Board;

import java.util.List;

public interface BoardsReader {
    List<Board> getBoards();

    Board getBoard(int boardId);
}
