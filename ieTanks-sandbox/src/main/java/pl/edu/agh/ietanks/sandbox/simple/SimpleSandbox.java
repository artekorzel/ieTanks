package pl.edu.agh.ietanks.sandbox.simple;

import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;

import java.util.List;
import java.util.UUID;

public class SimpleSandbox {

    private final BotService botService;
    private final BoardsReader boardsReader;
    private GamePlay gamePlay;

    public SimpleSandbox(BotService botService, BoardsReader boardsReader, GamePlay gamePlay) {
        this.botService = botService;
        this.boardsReader = boardsReader;
        this.gamePlay = gamePlay;
    }

    //TODO - add some exception handling
    public UUID startNewGameplay(int boardId, List<BotId> botIds) {
        Board board = boardsReader.getBoard(boardId);
        List<BotAlgorithm> bots = botService.fetch(botIds);
        return gamePlay.startGame(board, bots);
    }


}
