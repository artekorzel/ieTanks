package pl.edu.agh.ietanks.ranking.api;

import pl.edu.agh.ietanks.gameplay.game.api.GameId;

public interface RankingService {

    RankingId createRanking();

    void addGameToRanking(RankingId rankingId, GameId gameId);
}
