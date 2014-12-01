package pl.edu.agh.ietanks.gameplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.gameplay.testutils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context.xml"})
public class SmokeTest {
    @Autowired
    private GamePlay gameService;

    @Autowired
    private BoardsReader boardsReader;

    @Test
    public void runSimpleGame() throws InterruptedException {
        final Board board = boardsReader.getBoards().iterator().next();
        final List<BotAlgorithm> bots = new ArrayList<>();

        bots.add(new BotAlgorithm(new BotId("first-bot"), ResourceUtils.loadResourceFromFile("TestBot.py")));
        bots.add(new BotAlgorithm(new BotId("second-bot"), ResourceUtils.loadResourceFromFile("TestBot.py")));

        gameService.startGame(board, bots);

        Thread.sleep(10000);
    }
}
