package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class LeagueRepository {
    private final Map<LeagueId, LeagueState> leagues = Maps.newConcurrentMap();

    public LeagueState createLeague(String authorId) {
        final LeagueId id = LeagueId.of(UUID.randomUUID());
        final LeagueState league = new LeagueState(id, authorId);

        leagues.put(id, league);

        return league;
    }

    public Collection<LeagueState> fetchAll() {
        return leagues.values();
    }

    public Optional<LeagueState> fetchLeague(LeagueId leagueId) {
        return Optional.ofNullable(leagues.get(leagueId));
    }
}
