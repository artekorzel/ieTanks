package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.ImmutableList;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;

public class RoundExecutorFactory {
    private final RankingService rankingService;
    private final GamePlay gamePlayService;

    public RoundExecutorFactory(RankingService rankingService, GamePlay gamePlayService) {
        this.rankingService = rankingService;
        this.gamePlayService = gamePlayService;
    }

    public RoundExecutor createRoundExecutor(String boardId, ImmutableList<String> players) {
        return new RoundExecutor(boardId, players, rankingService, gamePlayService);
    }
}
