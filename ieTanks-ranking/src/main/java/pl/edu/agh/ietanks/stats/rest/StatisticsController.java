package pl.edu.agh.ietanks.stats.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.ranking.exceptions.GameNotFoundException;
import pl.edu.agh.ietanks.stats.service.StatsCalculator;

import java.util.Map;
import java.util.Optional;

@RestController
public class StatisticsController {

    @Autowired
    GameHistory gameHistory;

    @RequestMapping("/stats/{gameId}")
    public Map getStat(@PathVariable("gameId") String id) {
        GameId gameId = new GameId(id);
        Optional<Game> gameOptional = gameHistory.getGame(gameId);

        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }
        StatsCalculator statsCalculator = new StatsCalculator();
        return statsCalculator.calculateStatForGame(gameOptional.get());
    }
}
