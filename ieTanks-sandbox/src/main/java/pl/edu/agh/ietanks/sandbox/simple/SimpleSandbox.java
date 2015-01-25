package pl.edu.agh.ietanks.sandbox.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import java.util.List;
import java.util.logging.Logger;

@Service
public class SimpleSandbox implements Sandbox {
    private static Logger log = Logger.getLogger(SimpleSandbox.class.getName());

    private final GamePlay gamePlay;

    @Autowired
    public SimpleSandbox(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }

    //TODO - add some exception handling
    public GameId startNewGameplay(int boardId, List<BotId> botIds) {
        return gamePlay.startNewGameplay(boardId, botIds, (id) -> {
            log.info("Game " + id + " finished successfully");
        });
    }
}
