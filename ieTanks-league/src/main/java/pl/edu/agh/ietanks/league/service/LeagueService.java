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
        log.info("League started!");

        LeagueId id = LeagueId.of(UUID.randomUUID());
        League league = League.builder()
                .allGames(leagueDefinition.gamesNumber())
                .playedGames(leagueDefinition.gamesNumber())
                .authorId(userService.currentUser())
                .isActive(false)
                .id(id.toString())
                .build();

        leagues.put(id, league);

        final RoundExecutor executor = executorFactory.createRoundExecutor(
                leagueDefinition.boardId(), leagueDefinition.players());

        List<ZonedDateTime> roundsDateTimes = Lists.newArrayList();
        for (int i = 0; i < leagueDefinition.gamesNumber(); ++i) {
            ZonedDateTime roundDateTime = leagueDefinition.firstGameDatetime().plus(
                    leagueDefinition.interval().toDuration().multipliedBy(i));
            roundsDateTimes.add(roundDateTime);
        }

        for (ZonedDateTime roundDateTime : roundsDateTimes) {
            scheduler.schedule(executor, toDate(roundDateTime));
            log.info("Round scheduled for: " + toDate(roundDateTime));
        }

        return id;
    }

    public Optional<League> fetchLeague(LeagueId leagueId) {
        return Optional.ofNullable(leagues.get(leagueId));
    }

    public Collection<League> fetchAll() {
        return leagues.values();
    }
}
