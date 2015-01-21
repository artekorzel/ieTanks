package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.java.Log;
import org.springframework.scheduling.TaskScheduler;
import pl.edu.agh.ietanks.league.external.UserService;

import java.time.ZonedDateTime;
import java.util.*;

@Log
public class LeagueService {
    private final Map<LeagueId, League> leagues = Maps.newConcurrentMap();
    private final TaskScheduler scheduler;
    private final RoundExecutorFactory executorFactory;
    private final UserService userService;

    public LeagueService(TaskScheduler scheduler, RoundExecutorFactory executorFactory, UserService userService) {
        this.scheduler = scheduler;
        this.executorFactory = executorFactory;
        this.userService = userService;
    }

    private static Date toDate(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    public LeagueId startLeague(LeagueDefinition leagueDefinition) {
        LeagueId id = LeagueId.of(UUID.randomUUID());
        League league = createLeague(leagueDefinition, id);
        leagues.put(id, league);

        List<ZonedDateTime> roundsDateTimes = calculateRoundDateTimes(leagueDefinition);
        scheduleRounds(leagueDefinition, roundsDateTimes);

        log.info("League started!");
        return id;
    }

    public Optional<League> fetchLeague(LeagueId leagueId) {
        return Optional.ofNullable(leagues.get(leagueId));
    }

    public Collection<League> fetchAll() {
        return leagues.values();
    }

    private void scheduleRounds(LeagueDefinition leagueDefinition, List<ZonedDateTime> roundsDateTimes) {
        final RoundExecutor executor = executorFactory.createRoundExecutor(
                leagueDefinition.boardId(), leagueDefinition.players());

        for (ZonedDateTime roundDateTime : roundsDateTimes) {
            scheduler.schedule(executor, toDate(roundDateTime));
            log.info("Round scheduled for: " + toDate(roundDateTime));
        }
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

    private League createLeague(LeagueDefinition leagueDefinition, LeagueId id) {
        return League.builder()
                .allGames(leagueDefinition.gamesNumber())
                .playedGames(leagueDefinition.gamesNumber())
                .authorId(userService.currentUser())
                .isActive(false)
                .id(id.toString())
                .build();
    }
}
