package pl.edu.agh.ietanks.ranking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.ranking.api.RankingId;
import pl.edu.agh.ietanks.ranking.exceptions.GameNotFoundException;
import pl.edu.agh.ietanks.ranking.exceptions.RankingNotFoundException;
import pl.edu.agh.ietanks.ranking.internal.api.Ranking;
import pl.edu.agh.ietanks.ranking.internal.api.RankingCalculator;
import pl.edu.agh.ietanks.ranking.internal.api.RankingService;
import pl.edu.agh.ietanks.ranking.internal.api.RankingStorage;

import java.util.Optional;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    GameHistory gameHistory;

    @Autowired
    RankingStorage rankingStorage;

    @Autowired
    RankingsMerger rankingsMerger;

    @Autowired
    RankingCalculator rankingCalculator;

    @Override
    public Optional<Ranking> findRankingById(RankingId id) {
        return rankingStorage.findRankingById(id);
    }

    @Override
    public RankingId createRanking() {
        return rankingStorage.createRanking();
    }

    @Override
    public void addGameToRanking(RankingId rankingId, GameId gameId) {
        Optional<Ranking> rankingOptional = rankingStorage.findRankingById(rankingId);
        if (!rankingOptional.isPresent()) {
            throw new RankingNotFoundException(rankingId);
        }

        Optional<Game> gameOptional = gameHistory.getGame(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }

        Ranking gameRanking = rankingCalculator.calculateRankingForGame(gameOptional.get());
        Ranking ranking = rankingsMerger.apply(rankingOptional.get(), gameRanking);
        rankingStorage.updateRanking(ranking);
    }
}
