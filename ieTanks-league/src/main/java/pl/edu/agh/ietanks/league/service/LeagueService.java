package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.Maps;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Log
@Component
public class LeagueService {

    private final Map<LeagueId, League> leagues = Maps.newConcurrentMap();

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
        return id;
    }

    public Optional<League> fetchLeague(LeagueId leagueId) {
        return Optional.ofNullable(leagues.get(leagueId));
    }

    public Collection<League> fetchAll() {
        return leagues.values();
    }
}
