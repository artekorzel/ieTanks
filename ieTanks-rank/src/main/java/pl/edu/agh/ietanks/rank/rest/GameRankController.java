package pl.edu.agh.ietanks.rank.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.rank.api.GameRank;
import pl.edu.agh.ietanks.rank.api.GameRankService;
import pl.edu.agh.ietanks.rank.api.RankId;

import java.util.Optional;

@RestController
public class GameRankController {

    @Autowired
    GameRankService gameRankService;

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private static class GameRankNotFoundException extends RuntimeException {
        public GameRankNotFoundException(RankId rankId) {
            super("Game rank with id: " + rankId + " not found");
        }
    }

    @RequestMapping("/rank/{rankId}")
    public GameRank getRank(@PathVariable("rankId") String id) {
        RankId rankId = new RankId(id);
        Optional<GameRank> rank = gameRankService.getRank(rankId);
        if (rank.isPresent()) {
            return rank.get();
        }
        throw new GameRankNotFoundException(rankId);
    }
}
