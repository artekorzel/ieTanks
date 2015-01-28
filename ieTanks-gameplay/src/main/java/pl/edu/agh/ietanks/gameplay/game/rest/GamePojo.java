package pl.edu.agh.ietanks.gameplay.game.rest;

import pl.edu.agh.ietanks.gameplay.game.api.Game;

import java.util.ArrayList;
import java.util.List;

public class GamePojo {

    private final MapPojo map;
    private final List<GameRoundPojo> events = new ArrayList<GameRoundPojo>();
    private final List<PlayerInitialPositionPojo> initialPositions = new ArrayList<>();

    public GamePojo(Game game) {
        map = new MapPojo(game.getGameBoard());
        game.getGameEventsByRound().forEach((roundEvents) -> events.add(new GameRoundPojo(roundEvents)));
        game.getInitialParticipantsPositions().forEach((playerId, position) -> initialPositions.add(new PlayerInitialPositionPojo(playerId,position)));
    }


    public List<GameRoundPojo> getEvents() {
        return events;
    }

    public MapPojo getMap() {
        return map;
    }

    public List<PlayerInitialPositionPojo> getInitialPositions() {
        return initialPositions;
    }
}
