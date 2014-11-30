package pl.edu.agh.ietanks.gameplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.*;
import pl.edu.agh.ietanks.gameplay.testutils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context.xml"})
public class SmokeTest {
    @Autowired
    private GamePlay gameService;
    @Autowired
    private GameHistory gameHistory;

    @Autowired
    private BoardsReader boardsReader;

    @Test
    public void runSimpleGame() throws InterruptedException {
        //given
        final Board board = boardsReader.getBoards().iterator().next();
        final List<BotAlgorithm> bots = new ArrayList<>();

        bots.add(new BotAlgorithm(new BotId("first-bot"), ResourceUtils.loadResourceFromFile("TestBot.py")));
        bots.add(new BotAlgorithm(new BotId("second-bot"), ResourceUtils.loadResourceFromFile("TestBot.py")));

        //when
        UUID gameId = gameService.startGame(board, bots);
        Thread.sleep(10000);

        //then
        Game finishedGame = gameHistory.getGame(gameId);
        assertThat(finishedGame.getGameParticipants()).isEqualTo(bots);
        //TODO: we should set some test scenarios according to more advanced bot algorithm
    }
}
