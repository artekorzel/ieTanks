package pl.edu.agh.ietanks.stats.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.stats.api.Statistics;
import pl.edu.agh.ietanks.stats.api.TankStatistics;

import java.util.*;
import java.util.function.BinaryOperator;

@Service
public class StatsMerger implements BinaryOperator<Statistics> {

    @Override
    public Statistics apply(Statistics ranking1, Statistics ranking2) {
        Map<String, TankStatistics> tankStatisticsMap = new HashMap<>();
        for (TankStatistics tankStatistics : ranking1.getTankStatistics().values()) {
            String tankId = tankStatistics.getTankId();
            tankStatisticsMap.put(tankId, new TankStatistics(tankId, tankStatistics.getTanksShot(), tankStatistics.getBulletsShot(), tankStatistics.getRoundsPlayed()));
        }
        for (TankStatistics tankStatistics : ranking2.getTankStatistics().values()) {
            String tankId = tankStatistics.getTankId();
            if (tankStatisticsMap.containsKey(tankId)) {
                int bulletsShot = tankStatisticsMap.get(tankId).getBulletsShot() + tankStatistics.getBulletsShot();
                int roundsPlayed = tankStatisticsMap.get(tankId).getRoundsPlayed() + tankStatistics.getRoundsPlayed();
                int tanksShot = tankStatisticsMap.get(tankId).getTanksShot() + tankStatistics.getTanksShot();
                tankStatisticsMap.put(tankId, new TankStatistics(tankId, tanksShot, bulletsShot, roundsPlayed));
            } else {
                tankStatisticsMap.put(tankId, new TankStatistics(tankId, tankStatistics.getTanksShot(), tankStatistics.getBulletsShot(), tankStatistics.getRoundsPlayed()));
            }
        }

        List<TankStatistics> tankStatisticsList = sortTankStatisticsByTanksShot(tankStatisticsMap.values());
        Map<Integer, TankStatistics> rankingsMap = new HashMap<>();
        int tankPosition = 1;
        for (TankStatistics tankStatistics : tankStatisticsList) {
            rankingsMap.put(tankPosition++, tankStatistics);
        }
        return new Statistics(rankingsMap);
    }

    private List<TankStatistics> sortTankStatisticsByTanksShot(Collection<TankStatistics> values) {
        List<TankStatistics> tankStatisticsList = new LinkedList<>(values);
        tankStatisticsList.sort((tankStatistics1, tankStatistics2) -> {
            int tShot1 = tankStatistics1.getTanksShot();
            int tShot2 = tankStatistics2.getTanksShot();
            if (tShot1 < tShot2) {
                return 1;
            } else if (tShot1 > tShot2) {
                return -1;
            }
            return 0;
        });
        return tankStatisticsList;
    }
}
