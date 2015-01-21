package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.league.external.RankingService;

import java.util.List;
import java.util.stream.Collectors;

@Log
public class RoundExecutor implements Runnable {
    private final String boardId;
    private final ImmutableList<String> players;

    private final GamePlay gameplayService;
    private final RankingService rankingService;

    public RoundExecutor(String boardId, ImmutableList<String> players, RankingService rankingService, GamePlay gameplayService) {
        this.boardId = boardId;
        this.players = players;
        this.rankingService = rankingService;
        this.gameplayService = gameplayService;
    }

    @Override
    public void run() {

        final List<BotId> bots = players.stream()
                .map(BotId::new)
                /*.map(this::fetchBot)
                .filter(Optional::isPresent)
                .map(Optional::get)*/
                .collect(Collectors.toList());

        if (bots.isEmpty()) {
            log.warning("No bots available for this round for players: " + players);
            return;
        }

        final GameId gameId = gameplayService.startNewGameplay(Integer.parseInt(boardId), bots);

        log.info("Game " + gameId + " started");

        // ranking
    }
}
