package pl.edu.agh.ietanks.league.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Log
@Component
public class LeagueService {

    public void startLeague(LeagueDefinition leagueDefinition) {
        log.info("League started!");
    }
}
