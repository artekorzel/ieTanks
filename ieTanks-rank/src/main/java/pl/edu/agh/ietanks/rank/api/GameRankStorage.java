package pl.edu.agh.ietanks.rank.api;

import java.util.Optional;

public interface GameRankStorage {
    RankId store(GameRank gameRank);

    Optional<GameRank> get(RankId rankId);
}
