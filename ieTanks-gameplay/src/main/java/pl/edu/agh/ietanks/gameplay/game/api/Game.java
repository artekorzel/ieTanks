package pl.edu.agh.ietanks.gameplay.game.api;

import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.engine.api.events.Event;

import java.util.List;

public interface Game {
    public Integer getId();
    public List<Event> getGameEvents();
    public List<BotAlgorithm> getGameParticipants();
}
