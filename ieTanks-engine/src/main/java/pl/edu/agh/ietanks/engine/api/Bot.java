package pl.edu.agh.ietanks.engine.api;

/**
 * Bot behavior/logic interface
 */
public interface Bot {
    /**
     * Decides what move should be performed when the bot has its turn.
     * <p/>
     * The chosen action may be rejected if it is not valid. Bot looses his turn in this case.
     *
     * @param currentBoard current state of the board
     * @return Action which bot wants to perform
     */
    Action performAction(GameplayBoardView currentBoard);

    String id();
}
