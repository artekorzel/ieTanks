package pl.edu.agh.ietanks.ranking.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.ranking.api.Ranking;
import pl.edu.agh.ietanks.ranking.api.RankingId;
import pl.edu.agh.ietanks.ranking.api.RankingService;
import pl.edu.agh.ietanks.ranking.exceptions.RankingNotFoundException;

import java.util.Optional;

@RestController
public class RankingController {

    @Autowired
    RankingService rankingService;

    @RequestMapping("/ranking/{id}")
    public Ranking getRanking(@PathVariable("id") String id) {
        RankingId rankingId = new RankingId(id);
        Optional<Ranking> ranking = rankingService.findRankingById(rankingId);
        if (ranking.isPresent()) {
            return ranking.get();
        }
        throw new RankingNotFoundException(rankingId);
    }
}
