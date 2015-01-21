package pl.edu.agh.ietanks.league.external;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;

@Log
@Component
public class RankingService {
    public RankingId createRanking() {
        log.info("Ranking created");

        return new RankingId();
    }

    public void addGameToRanking(RankingId rankingId, GameId gameId) {
        log.info("Game " + gameId + " added to ranking " + rankingId);
    }
}
