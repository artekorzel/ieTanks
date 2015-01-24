package pl.edu.agh.ietanks.rank.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameRank {

    private RankId rankId;
    private Map<Integer, TankRank> rank;

    public GameRank() {
        this(new HashMap<>());
    }

    public GameRank(Map<Integer, TankRank> rank) {
        this.rank = rank;
    }

    public RankId getRankId() {
        return rankId;
    }

    public void setRankId(RankId rankId) {
        this.rankId = rankId;
    }

    public Map<Integer, TankRank> getRank() {
        return Collections.unmodifiableMap(rank);
    }
}
