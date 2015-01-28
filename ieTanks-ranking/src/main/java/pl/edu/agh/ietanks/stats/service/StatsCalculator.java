package pl.edu.agh.ietanks.stats.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.MissileCreated;
import pl.edu.agh.ietanks.engine.api.events.TankMoved;
import pl.edu.agh.ietanks.gameplay.game.api.Game;

import java.util.*;

@Service
public class StatsCalculator{

    public Map<String, Object> calculateStatForGame(Game game) {
        Map<String, Integer> gameStats = new HashMap<>();
        List<Event> gameEvents = game.getGameEvents();
        List<String> bulletsShot = new ArrayList<>();
        List<String> tankMoves = new ArrayList<>();
        int roundsPlayed = game.getGameEventsByRound().size();
        for (Event event : gameEvents) {
                if (event instanceof MissileCreated) {
                    String tankId = ((MissileCreated) event).tankId();
                    bulletsShot.add(tankId);
                }
                else if(event instanceof TankMoved){
                    String tankId = ((TankMoved) event).tankId();
                    tankMoves.add(tankId);
                }
        }
        gameStats.put("bulletsShot", bulletsShot.size());
        gameStats.put("roundsPlayed", roundsPlayed);
        gameStats.put("tankMoves", tankMoves.size());

        HashMap<String, Integer> bulletsPerTank = new HashMap<>();
        for(String tankId : bulletsShot) {
            if(bulletsPerTank.containsKey(tankId)) {
                bulletsPerTank.put(tankId, bulletsPerTank.get(tankId)+1);
            }
            else{ bulletsPerTank.put(tankId, 1); }
        }
        HashMap<String, Integer> movesPerTank = new HashMap<>();
        for(String tankId : tankMoves) {
            if(movesPerTank.containsKey(tankId)) {
                movesPerTank.put(tankId, movesPerTank.get(tankId)+1);
            }
            else{ movesPerTank.put(tankId, 1); }
        }

        ArrayList<Map> tanksStats = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : bulletsPerTank.entrySet()){
            HashMap<String, String> tank = new HashMap<>();
            tank.put("tankId", entry.getKey());
            tank.put("bulletsShot", entry.getValue().toString());
            tank.put("tankMoves", movesPerTank.get(entry.getKey()).toString());
            tanksStats.add(tank);
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("gameStats", gameStats);
        statistics.put("tanksStats", tanksStats);

        return statistics;
    }
}
