package pl.edu.agh.ietanks.league.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Log
@Component
public class LeagueService {

    public void startLeague(LeagueDefinition leagueDefinition) {
        log.info("League started!");
    }

    public Optional<League> fetchGame(LeagueId leagueId) {
        return Optional.empty();
    }
}
