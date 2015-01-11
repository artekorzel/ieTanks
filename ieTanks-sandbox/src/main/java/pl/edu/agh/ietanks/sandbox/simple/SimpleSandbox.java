package pl.edu.agh.ietanks.sandbox.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleSandbox implements Sandbox {

    private final BotService botService;
    private final BoardsReader boardsReader;
    private final GamePlay gamePlay;

    @Autowired
    public SimpleSandbox(@Qualifier("simpleBotService")BotService botService, BoardsReader boardsReader, GamePlay gamePlay) {
        this.botService = botService;
        this.boardsReader = boardsReader;
        this.gamePlay = gamePlay;
    }

    //TODO - add some exception handling
    public GameId startNewGameplay(int boardId, List<BotId> botIds) {
        Board board = boardsReader.getBoard(boardId);
        List<BotAlgorithm> bots = botService.fetch(botIds);
        return gamePlay.startGame(board, bots);
    }

    @Override
    public List<BotId> getAvailableBots() {
        return botService.listAvailableBots();
    }

    @Override
    public List<Integer> getAvailableBoards() {
        return boardsReader.getBoards().stream().map(Board::getId).collect(Collectors.toList());
    }


}
