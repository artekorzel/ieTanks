package pl.edu.agh.ietanks.ranking.internal.api;

import pl.edu.agh.ietanks.ranking.api.RankingId;

import java.util.Optional;

public interface RankingService extends pl.edu.agh.ietanks.ranking.api.RankingService {

    Optional<Ranking> findRankingById(RankingId rankingId);
}
