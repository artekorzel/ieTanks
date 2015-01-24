package pl.edu.agh.ietanks.rank.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.rank.api.GameRank;
import pl.edu.agh.ietanks.rank.api.TankRank;

import java.util.*;
import java.util.function.BinaryOperator;

@Service
public class GameRanksMerger implements BinaryOperator<GameRank> {

    @Override
    public GameRank apply(GameRank gameRank1, GameRank gameRank2) {
        Map<String, TankRank> tankPointMap = new HashMap<>();
        for (TankRank tankRank : gameRank1.getRank().values()) {
            String tankId = tankRank.getTankId();
            tankPointMap.put(tankId, new TankRank(tankId, tankRank.getPoints()));
        }
        for (TankRank tankRank : gameRank2.getRank().values()) {
            String tankId = tankRank.getTankId();
            if (tankPointMap.containsKey(tankId)) {
                int points = tankPointMap.get(tankId).getPoints() + tankRank.getPoints();
                tankPointMap.put(tankId, new TankRank(tankId, points));
            } else {
                tankPointMap.put(tankId, new TankRank(tankId, tankRank.getPoints()));
            }
        }

        List<TankRank> tankRankList = sortTankRanksDescending(tankPointMap.values());
        Map<Integer, TankRank> rankMap = new HashMap<>();
        int tankPosition = 1;
        for (TankRank tankRank : tankRankList) {
            rankMap.put(tankPosition++, tankRank);
        }
        return new GameRank(rankMap);
    }

    private List<TankRank> sortTankRanksDescending(Collection<TankRank> values) {
        List<TankRank> tankRankList = new LinkedList<>(values);
        tankRankList.sort((tankRank1, tankRank2) -> {
            int points1 = tankRank1.getPoints();
            int points2 = tankRank2.getPoints();
            if (points1 < points2) {
                return 1;
            } else if (points1 > points2) {
                return -1;
            }
            return 0;
        });
        return tankRankList;
    }
}
