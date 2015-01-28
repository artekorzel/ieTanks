package pl.edu.agh.ietanks.boards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.boards.api.BoardsReader;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BoardsRestController {

    @Autowired
    BoardsReader boardsReader;

    @RequestMapping("/board")
    List<BoardPojo> getBoards() {
        return boardsReader.getBoards().stream().map(board -> new BoardPojo(board.getId())).collect(Collectors.toList());
    }
}
