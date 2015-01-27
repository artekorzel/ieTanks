package pl.edu.agh.ietanks.ranking.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.ranking.api.TankRanking;
import pl.edu.agh.ietanks.ranking.internal.api.Ranking;

import java.util.*;
import java.util.function.BinaryOperator;

@Service
public class RankingsMerger implements BinaryOperator<Ranking> {

    @Override
    public Ranking apply(Ranking ranking1, Ranking ranking2) {
        Map<String, TankRanking> tankRankingsMap = mergeRankingsIntoMap(ranking1, ranking2);
        List<TankRanking> tankRankingList = sortTankRankingsDescending(tankRankingsMap.values());
        Map<Integer, TankRanking> rankingsMap = buildTankRankingsMapWithPositions(tankRankingList);
        return new Ranking(rankingsMap);
    }

    private Map<String, TankRanking> mergeRankingsIntoMap(Ranking ranking1, Ranking ranking2) {
        Map<String, TankRanking> tankRankingsMap = new HashMap<>();
        for (TankRanking tankRanking : ranking1.getTankRankings().values()) {
            String tankId = tankRanking.getTankId();
            tankRankingsMap.put(tankId, new TankRanking(tankId, tankRanking.getPoints()));
        }
        for (TankRanking tankRanking : ranking2.getTankRankings().values()) {
            String tankId = tankRanking.getTankId();
            if (tankRankingsMap.containsKey(tankId)) {
                int points = tankRankingsMap.get(tankId).getPoints() + tankRanking.getPoints();
                tankRankingsMap.put(tankId, new TankRanking(tankId, points));
            } else {
                tankRankingsMap.put(tankId, new TankRanking(tankId, tankRanking.getPoints()));
            }
        }
        return tankRankingsMap;
    }

    private List<TankRanking> sortTankRankingsDescending(Collection<TankRanking> values) {
        List<TankRanking> tankRankingList = new LinkedList<>(values);
        tankRankingList.sort((tankRanking1, tankRanking2) -> {
            int points1 = tankRanking1.getPoints();
            int points2 = tankRanking2.getPoints();
            if (points1 < points2) {
                return 1;
            } else if (points1 > points2) {
                return -1;
            }
            return 0;
        });
        return tankRankingList;
    }

    private Map<Integer, TankRanking> buildTankRankingsMapWithPositions(List<TankRanking> tankRankingList) {
        Map<Integer, TankRanking> rankingsMap = new HashMap<>();
        int tankPosition = 1;
        for (TankRanking tankRanking : tankRankingList) {
            rankingsMap.put(tankPosition++, tankRanking);
        }
        return rankingsMap;
    }
}
