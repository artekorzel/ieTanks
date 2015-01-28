package pl.edu.agh.ietanks.gameplay.game.rest;


import pl.edu.agh.ietanks.engine.api.events.AbstractMissileEvent;
import pl.edu.agh.ietanks.engine.api.events.AbstractTankEvent;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.EventGroup;

import java.util.ArrayList;
import java.util.List;

public class GameRoundPojo {
    private final List<PlayerEventPojo> players = new ArrayList<>();
    private final List<MissileEventPojo> missiles = new ArrayList<>();

    public List<PlayerEventPojo> getPlayers() {
        return players;
    }

    public List<MissileEventPojo> getMissiles() {
        return missiles;
    }

    public GameRoundPojo(List<Event> roundEvents){
        roundEvents.stream()
                .filter( e -> e.getEventGroup() == EventGroup.MISSILES)
                .forEach( missileEvent -> missiles.add(new MissileEventPojo((AbstractMissileEvent) missileEvent)));
        roundEvents.stream()
                .filter( e -> e.getEventGroup() == EventGroup.TANKS)
                .forEach( tankEvent -> players.add(new PlayerEventPojo((AbstractTankEvent) tankEvent)));
    }
}
