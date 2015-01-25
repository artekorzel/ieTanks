package pl.edu.agh.ietanks.ranking.api;

import java.util.Optional;

public interface RankingStorage {
    RankingId createRanking();

    void updateRanking(Ranking ranking);

    Optional<Ranking> findRankingById(RankingId rankingId);
}
