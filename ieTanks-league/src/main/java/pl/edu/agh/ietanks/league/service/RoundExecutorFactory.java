package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.ImmutableList;
import pl.edu.agh.ietanks.boards.BoardsBean;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;

public class RoundExecutorFactory {
    private final BoardsBean boardService;
    private final BotService botService;
    private final RankingService rankingService;
    private final GamePlay gamePlayService;

    public RoundExecutorFactory(BoardsBean boardService, BotService botService, RankingService rankingService,
                                GamePlay gamePlayService) {
        this.boardService = boardService;
        this.botService = botService;
        this.rankingService = rankingService;
        this.gamePlayService = gamePlayService;
    }

    public RoundExecutor createRoundExecutor(String boardId, ImmutableList<String> players) {
        return new RoundExecutor(boardId, players, boardService, botService, rankingService, gamePlayService);
    }
}
