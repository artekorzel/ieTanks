package pl.edu.agh.ietanks.engine.api;

import pl.edu.agh.ietanks.engine.api.events.RoundResults;

/**
 * Game engine interface.
 *
 * You call only nextMove() till the end of the game (RoundResults.isGameFinished).
 */
public interface Engine {
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
