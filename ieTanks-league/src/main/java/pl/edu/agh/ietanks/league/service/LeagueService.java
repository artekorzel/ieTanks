package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.Lists;
import lombok.extern.java.Log;
import org.springframework.scheduling.TaskScheduler;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.league.external.RankingId;
import pl.edu.agh.ietanks.league.external.RankingService;
import pl.edu.agh.ietanks.league.external.UserService;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Log
public class LeagueService {
    private final TaskScheduler scheduler;
    private final UserService userService;
    private final LeagueRepository leagueRepository;
    private final GamePlay gameplayService;
    private final RankingService rankingService;

    public LeagueService(TaskScheduler scheduler, UserService userService, LeagueRepository leagueRepository,
                         GamePlay gamePlayService, RankingService rankingService) {
        this.scheduler = scheduler;
        this.userService = userService;
        this.leagueRepository = leagueRepository;
        this.gameplayService = gamePlayService;
        this.rankingService = rankingService;
    }

    private static Date toDate(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    public LeagueId startLeague(LeagueDefinition leagueDefinition) {
        if (leagueDefinition.players().isEmpty()) {
            log.warning("No bots available for this round for players: " + leagueDefinition.players());
        }

        final String authorId = userService.currentUser();
        final LeagueState league = leagueRepository.createLeague(authorId);
        final RankingId rankingId = rankingService.createRanking();

        List<ZonedDateTime> roundsDateTimes = calculateRoundDateTimes(leagueDefinition);

        for (ZonedDateTime roundDateTime : roundsDateTimes) {
            final LeagueState.RoundId roundId = league.createRound(roundDateTime);

            final Consumer<GameId> onGameFinished = (GameId gameId) -> {
                league.finishRound(roundId);
                rankingService.addGameToRanking(rankingId, gameId);
            };

            final Runnable task = () -> {
                final GameId gameId = gameplayService.startNewGameplay(
                        leagueDefinition.boardId(),
                        leagueDefinition.players(),
                        onGameFinished);

                league.startRound(roundId);
                log.info("Game " + gameId + " started");
            };

            scheduler.schedule(task, toDate(roundDateTime));
            log.info("Round scheduled for: " + toDate(roundDateTime));
        }

        log.info("League started!");
        return league.id();
    }

    public Optional<League> fetchLeague(LeagueId leagueId) {
        final Optional<LeagueState> fetchedLeague = leagueRepository.fetchLeague(leagueId);
        return fetchedLeague.map(LeagueState::toLeague);
    }

    public Collection<League> fetchAll() {
        return leagueRepository.fetchAll().stream()
                .map(LeagueState::toLeague)
                .collect(Collectors.toList());
    }

    private List<ZonedDateTime> calculateRoundDateTimes(LeagueDefinition leagueDefinition) {
        List<ZonedDateTime> roundsDateTimes = Lists.newArrayList();
        for (int i = 0; i < leagueDefinition.gamesNumber(); ++i) {
            ZonedDateTime roundDateTime = leagueDefinition.firstGameDatetime().plus(
                    leagueDefinition.interval().toDuration().multipliedBy(i));
            roundsDateTimes.add(roundDateTime);
        }
        return roundsDateTimes;
    }
}
