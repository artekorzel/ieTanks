package pl.edu.agh.ietanks.league.rest;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.league.service.LeagueDefinition;
import pl.edu.agh.ietanks.league.service.LeagueService;

import java.util.Collections;
import java.util.List;

@Log
@RestController
public class LeagueRestPort {
    @Autowired
    private LeagueService leagueService;

    @RequestMapping(value = "/league", method = RequestMethod.POST)
    public void startLeague(@RequestBody LeagueDefinitionDTO league) {
        LeagueDefinition leagueDefinition = league.toLeagueDefinition();
        leagueService.startLeague(leagueDefinition);
    }

    @RequestMapping("/league")
    public List<LeagueDTO> getGameIds() {
        log.info("GET");
        return Collections.emptyList();
    }
}
