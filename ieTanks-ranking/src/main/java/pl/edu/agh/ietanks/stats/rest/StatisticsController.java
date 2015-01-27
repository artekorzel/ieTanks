package pl.edu.agh.ietanks.stats.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.stats.api.Statistics;
import pl.edu.agh.ietanks.stats.api.StatId;
import pl.edu.agh.ietanks.stats.api.StatService;
import pl.edu.agh.ietanks.stats.exceptions.StatNotFoundException;

import java.util.Optional;

@RestController
public class StatisticsController {

    @Autowired
    StatService statService;

    @RequestMapping("/stats/{id}")
    public Statistics getStat(@PathVariable("id") String id) {
        StatId statId = new StatId(id);
        Optional<Statistics> stat = statService.findStatById(statId);
        if (stat.isPresent()) {
            return stat.get();
        }
        throw new StatNotFoundException(statId);
    }
}
