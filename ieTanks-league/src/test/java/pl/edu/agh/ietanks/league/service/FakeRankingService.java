package pl.edu.agh.ietanks.league.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.ranking.api.RankingId;
import pl.edu.agh.ietanks.ranking.api.RankingService;

@Log
@Component
public class FakeRankingService implements RankingService {

    @Override
    public RankingId createRanking() {
        log.info("Ranking created");
        return new RankingId("1");
    }

    @Override
    public void addGameToRanking(RankingId rankingId, GameId gameId) {
        log.info("Game " + gameId + " added to ranking " + rankingId);
    }
}
