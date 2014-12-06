package pl.edu.agh.ietanks.gameplay.game.api;

import pl.edu.agh.ietanks.engine.api.events.Event;

import java.util.List;
import java.util.UUID;

public interface Game {
    public GameId getId();

    public List<Event> getGameEvents();

    public List<BotAlgorithm> getGameParticipants();
}
