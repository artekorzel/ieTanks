package pl.edu.agh.ietanks.stats.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.MissileCreated;
import pl.edu.agh.ietanks.engine.api.events.TankMoved;
import pl.edu.agh.ietanks.gameplay.game.api.Game;

import java.util.*;

@Service
public class StatsCalculator{

    public Map<String, Integer> calculateStatForGame(Game game) {
        Map<String, Integer> stats = new HashMap<>();
        List<Event> gameEvents = game.getGameEvents();
        int bulletsShot = 0;
        int roundsPlayed = game.getGameEventsByRound().size();
        int tankMoves = 0;
        for (Event event : gameEvents) {
                if (event instanceof MissileCreated) {
                    bulletsShot++;
                }
                else if(event instanceof TankMoved){
                    tankMoves++;
                }
        }
        stats.put("bulletsShot",bulletsShot);
        stats.put("roundsPlayed",roundsPlayed);
        stats.put("tankMoves",tankMoves);
        return stats;
    }
}
