package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.java.Log;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Log
public class LeagueService {
    private final Map<LeagueId, League> leagues = Maps.newConcurrentMap();
    private final TaskScheduler scheduler;
    private final RoundExecutorFactory executorFactory;

    public LeagueService(TaskScheduler scheduler, RoundExecutorFactory executorFactory) {
        this.scheduler = scheduler;
        this.executorFactory = executorFactory;
    }

    private static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    public LeagueId startLeague(LeagueDefinition leagueDefinition) {
        log.info("League started!");

        LeagueId id = LeagueId.of(UUID.randomUUID());
        League league = League.builder()
                .allGames(leagueDefinition.gamesNumber())
                .playedGames(leagueDefinition.gamesNumber())
                .authorId("mequrel")
                .isActive(false)
                .id(id.toString())
                .build();

        leagues.put(id, league);

        final RoundExecutor executor = executorFactory.createRoundExecutor(
                leagueDefinition.boardId(), leagueDefinition.players());

        List<LocalDateTime> roundsDateTimes = Lists.newArrayList();
        for (int i = 0; i < leagueDefinition.gamesNumber(); ++i) {
            LocalDateTime roundDateTime = leagueDefinition.firstGameDatetime().plus(
                    leagueDefinition.interval().toDuration().multipliedBy(i));
            roundsDateTimes.add(roundDateTime);
        }

        for (LocalDateTime roundDateTime : roundsDateTimes) {
            scheduler.schedule(executor, toDate(roundDateTime));
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
