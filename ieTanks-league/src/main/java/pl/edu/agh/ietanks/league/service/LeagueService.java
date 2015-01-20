package pl.edu.agh.ietanks.league.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log
@Component
public class LeagueService {

    public void startLeague(LeagueDefinition leagueDefinition) {
        log.info("League started!");
    }

    public Optional<League> fetchLeague(LeagueId leagueId) {
        return Optional.empty();
    }

    public List<League> fetchAll() {
        return Arrays.asList(
                League.builder()
                        .id("644e1dd7-2a7f-18fb-b8ed-ed78c3f92c2b")
                        .authorId("mequrel")
                        .allGames(10)
                        .playedGames(6)
                        .isActive(true)
                        .nextScheduledGame(LocalDateTime.now())
                        .build()
        );
    }
}
