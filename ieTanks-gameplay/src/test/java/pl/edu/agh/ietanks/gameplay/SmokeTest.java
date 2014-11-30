package pl.edu.agh.ietanks.gameplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
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

    static class SimpleBotAlgorithm implements BotAlgorithm {
        private final int id;

        SimpleBotAlgorithm(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getPythonCode() {
            return ResourceUtils.loadResourceFromFile("TestBot.py");
        }
    }

    @Test
    public void runSimpleGame() throws InterruptedException {
        final Board board = boardsReader.getBoards().iterator().next();
        final List<BotAlgorithm> bots = new ArrayList<>();

        bots.add(new SimpleBotAlgorithm(1));
        bots.add(new SimpleBotAlgorithm(2));

        gameService.startGame(board, bots);

        Thread.sleep(10000);
    }
}
