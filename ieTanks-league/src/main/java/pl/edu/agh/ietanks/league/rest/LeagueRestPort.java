package pl.edu.agh.ietanks.league.rest;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ietanks.league.service.League;
import pl.edu.agh.ietanks.league.service.LeagueDefinition;
import pl.edu.agh.ietanks.league.service.LeagueId;
import pl.edu.agh.ietanks.league.service.LeagueService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log
@RestController
public class LeagueRestPort {
    @Autowired
    private LeagueService leagueService;

    private static <T, U> List<T> transform(Collection<U> collection, Function<U, T> func) {
        return collection.stream().map(func).collect(Collectors.toList());
    }

    @RequestMapping(value = "/league", method = RequestMethod.POST)
    public void startLeague(@RequestBody LeagueDefinitionDTO league) {
        LeagueDefinition leagueDefinition = league.toLeagueDefinition();
        leagueService.startLeague(leagueDefinition);
    }

    @RequestMapping("/league/{id}")
    public Optional<LeagueDTO> getLeague(@ModelAttribute("id") String id, HttpServletResponse response) {
        Optional<League> leagueOption = leagueService.fetchLeague(LeagueId.of(id));

        if (!leagueOption.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return leagueOption.map(LeagueDTO::from);
    }

    @RequestMapping("/league")
    public List<LeagueDTO> getAllLeagues() {
        Collection<League> leagues = leagueService.fetchAll();
        return transform(leagues, LeagueDTO::from);
    }


}
