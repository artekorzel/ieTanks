package pl.edu.agh.ietanks.gameplay.game.api;

import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.bot.api.BotAlgorithm;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.engine.api.events.Event;

import java.util.List;
import java.util.Map;

public interface Game {
    public GameId getId();

    public List<Event> getGameEvents();

    public List<BotAlgorithm> getGameParticipants();

    public Board getGameBoard();

    public List<List<Event>> getGameEventsByRound();

    public Map<String, Position> getInitialParticipantsPositions();
}
