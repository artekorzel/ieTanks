package pl.edu.agh.ietanks.gameplay;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.bot.api.BotAlgorithm;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context.xml"})
public class SmokeTest {
    private static Logger log = Logger.getLogger(SmokeTest.class.getName());

    @Autowired
    private GameHistory gameHistory;

    @Autowired
    private GamePlay gameService;

    @Autowired
    private BoardsReader boardsReader;

    @Test
    public void runSimpleGame() throws InterruptedException {
        //given
        final Board board = boardsReader.getBoards().iterator().next();
        final List<BotId> botIds = Arrays.asList(new BotId("some-bot"), new BotId("some-other-bot"));

        //when
        GameId gameId = gameService.startNewGameplay(1, botIds, (id) -> {
            log.info("Game " + id + " finished successfully");
        });
        Thread.sleep(10000);

        //then
        Optional<Game> finishedGame = gameHistory.getGame(gameId);
        List<BotAlgorithm> gameParticipants = finishedGame.get().getGameParticipants();
        List<BotId> finishedGameBotIds = gameParticipants.stream().map(BotAlgorithm::id).collect(Collectors.toList());

        assertThat(finishedGameBotIds).isEqualTo(botIds);
        //TODO: we should set some test scenarios according to more advanced bot algorithm
    }
}
