package pl.edu.agh.ietanks.ranking.internal.api;

import pl.edu.agh.ietanks.ranking.api.RankingId;
import pl.edu.agh.ietanks.ranking.api.TankRanking;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Ranking {

    private RankingId rankingId;
    private Map<Integer, TankRanking> tankRankings;

    public Ranking() {
        this(new HashMap<>());
    }

    public Ranking(Map<Integer, TankRanking> tankRankings) {
        this.tankRankings = tankRankings;
    }

    public RankingId getRankingId() {
        return rankingId;
    }

    public void setRankingId(RankingId rankingId) {
        this.rankingId = rankingId;
    }

    public Map<Integer, TankRanking> getTankRankings() {
        return Collections.unmodifiableMap(tankRankings);
    }
}
