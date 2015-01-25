package pl.edu.agh.ietanks.ranking.api;

import pl.edu.agh.ietanks.gameplay.game.api.GameId;

import java.util.Optional;

public interface RankingService {

    Optional<Ranking> findRankingById(RankingId rankingId);

    RankingId createRanking();

    void addGameToRanking(RankingId rankingId, GameId gameId);
}
