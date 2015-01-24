package pl.edu.agh.ietanks.rank.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.rank.api.GameRank;
import pl.edu.agh.ietanks.rank.api.GameRankStorage;
import pl.edu.agh.ietanks.rank.api.RankId;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryGameRankStorage implements GameRankStorage {

    private static final AtomicLong rankIdBase = new AtomicLong();
    private static final Map<RankId, GameRank> gameRanksCache = new ConcurrentHashMap<>();

    @Override
    public RankId store(GameRank gameRank) {
        RankId rankId = new RankId("" + rankIdBase.incrementAndGet());
        gameRank.setRankId(rankId);
        gameRanksCache.put(rankId, gameRank);
        return rankId;
    }

    @Override
    public Optional<GameRank> get(RankId rankId) {
        if (gameRanksCache.containsKey(rankId)) {
            return Optional.of(gameRanksCache.get(rankId));
        }
        return Optional.empty();
    }
}
