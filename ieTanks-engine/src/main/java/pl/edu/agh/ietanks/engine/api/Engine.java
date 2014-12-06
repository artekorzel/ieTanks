package pl.edu.agh.ietanks.engine.api;

import pl.edu.agh.ietanks.engine.api.events.RoundResults;

import java.util.List;

/**
 * Game engine interface. Need to be initialized first.
 * After that, you call only nextMove() till the end of the game (that is notified by one of the events).
 */
public interface Engine {
    /**
     * Initializes the engine. Needs board and bots definition.
     *
     * @param initialBoard  a board model we are starting with
     * @param bots          list of bot definitions, capable of performing moves
     * @param configuration game configuration options
     */
    void setup(BoardDefinition initialBoard, List<? extends Bot> bots, GameConfig configuration);

    /**
     * Performs next move.
     *
     * @return list of events that happened due to the move (e.g. tank moved, missile fired, game finished)
     */
    RoundResults nextMove();

    /**
     * Retrieves current state of the game board.
     */
    GameplayBoardView currentBoard();
}
