package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import pl.edu.agh.ietanks.boards.BoardsBean;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.BotServiceUnavailableException;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log
public class RoundExecutor implements Runnable {
    private final String boardId;
    private final ImmutableList<String> players;

    private final BoardsBean boardService;
    private final BotService botService;
    private final GamePlay gameplayService;
    private final RankingService rankingService;

    public RoundExecutor(String boardId, ImmutableList<String> players, BoardsBean boardService,
                         BotService botService, RankingService rankingService, GamePlay gameplayService) {
        this.boardId = boardId;
        this.players = players;
        this.boardService = boardService;
        this.botService = botService;
        this.rankingService = rankingService;
        this.gameplayService = gameplayService;
    }

    @Override
    public void run() {
        final Board board = boardService.getBoard(Integer.parseInt(boardId));

        final List<BotAlgorithm> bots = players.stream()
                .map(BotId::new)
                .map(this::fetchBot)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        if (bots.isEmpty()) {
            log.warning("No bots available for this round for players: " + players);
            return;
        }

        final GameId gameId = gameplayService.startGame(board, bots);

        log.info("Game " + gameId + " started");

        // ranking
    }

    private Optional<BotAlgorithm> fetchBot(BotId id) {
        try {
            log.info("Trying to fetch: " + id);
            return Optional.ofNullable(botService.fetch(id));
        } catch (BotServiceUnavailableException e) {
            return Optional.empty();
        }
    }
}
