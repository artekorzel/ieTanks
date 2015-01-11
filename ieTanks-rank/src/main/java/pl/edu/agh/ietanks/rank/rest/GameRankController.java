package pl.edu.agh.ietanks.rank.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.rank.api.GameRank;
import pl.edu.agh.ietanks.rank.api.GameRankService;

import java.util.Optional;

@RestController
public class GameRankController {

    @Autowired
    GameRankService gameRankService;

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private static class GameRankNotFoundException extends RuntimeException {
        public GameRankNotFoundException(GameId gameId){
            super("Game rank for game with id not found in history: " + gameId);
        }
    }

    @RequestMapping("/rank/{gameId}")
    public GameRank getRankForGame(@PathVariable("gameId") String gameId) {
        Optional<GameRank> rank = gameRankService.getRankForGame(new GameId(gameId));
        if (rank.isPresent()) {
            return rank.get();
        }
        throw new GameRankNotFoundException(new GameId(gameId));
    }
}
