package pl.edu.agh.ietanks.ranking.service;

import org.springframework.stereotype.Repository;
import pl.edu.agh.ietanks.ranking.api.RankingId;
import pl.edu.agh.ietanks.ranking.exceptions.RankingNotFoundException;
import pl.edu.agh.ietanks.ranking.internal.api.Ranking;
import pl.edu.agh.ietanks.ranking.internal.api.RankingStorage;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryRankingStorage implements RankingStorage {

    private static final AtomicLong rankIdBase = new AtomicLong();
    private static final Map<RankingId, Ranking> gameRanksCache = new ConcurrentHashMap<>();

    @Override
    public RankingId createRanking() {
        RankingId rankingId = new RankingId(Long.toString(rankIdBase.incrementAndGet()));
        Ranking ranking = new Ranking();
        ranking.setRankingId(rankingId);
        gameRanksCache.put(rankingId, ranking);
        return rankingId;
    }

    @Override
    public void updateRanking(Ranking ranking) {
        RankingId rankingId = ranking.getRankingId();
        if (gameRanksCache.containsKey(rankingId)) {
            gameRanksCache.put(rankingId, ranking);
            return;
        }
        throw new RankingNotFoundException(rankingId);
    }

    @Override
    public Optional<Ranking> findRankingById(RankingId rankingId) {
        return Optional.ofNullable(gameRanksCache.get(rankingId));
    }
}
