package pl.edu.agh.ietanks.sandbox.simple.api;

import pl.edu.agh.ietanks.gameplay.game.api.BotId;

import java.util.List;
import java.util.UUID;

public interface Sandbox {
    /**
     * Initializes and starts new game
     *
     * @param boardId id of the board
     * @param botIds list of bot ids, which can take part in testing plays
     */
    UUID startNewGameplay(int boardId, List<BotId> botIds);

    /**
     * Fetches ids of bots capable of taking part in testing plays
     *
     * @return list of bot ids
     */
    List<BotId> getAvailableBots();

    /**
     * Fetches ids of boards capable of taking part in testing plays
     *
     * @return list of board ids
     */
    List<Integer> getAvailableBoards();
}
