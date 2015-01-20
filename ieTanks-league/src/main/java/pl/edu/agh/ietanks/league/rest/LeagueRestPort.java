package pl.edu.agh.ietanks.league.rest;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.List;

@Log
//@RestController
public class LeagueRestPort {
    @Autowired
    private LeagueService leagueService;

    @RequestMapping(value = "/league", method = RequestMethod.POST)
    public void startLeague(@RequestBody LeagueDefinition league) {
        log.info("League started!");
    }

    @RequestMapping("/league")
    public List<League> getGameIds() {
        log.info("GET");
        return Collections.emptyList();
    }
}
