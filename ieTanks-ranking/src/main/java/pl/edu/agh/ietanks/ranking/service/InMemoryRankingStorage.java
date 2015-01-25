package pl.edu.agh.ietanks.ranking.service;

import org.springframework.stereotype.Repository;
import pl.edu.agh.ietanks.ranking.api.Ranking;
import pl.edu.agh.ietanks.ranking.api.RankingId;
import pl.edu.agh.ietanks.ranking.api.RankingStorage;
import pl.edu.agh.ietanks.ranking.exceptions.RankingNotFoundException;

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
        RankingId rankingId = new RankingId("" + rankIdBase.incrementAndGet());
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
        }
        throw new RankingNotFoundException(rankingId);
    }

    @Override
    public Optional<Ranking> findRankingById(RankingId rankingId) {
        if (gameRanksCache.containsKey(rankingId)) {
            return Optional.of(gameRanksCache.get(rankingId));
        }
        return Optional.empty();
    }
}
