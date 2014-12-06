package pl.edu.agh.ietanks.sandbox.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class,
                      classes = SimpleSandboxTestContextConfiguration.class)
public class SimpleSandboxTest {

    @Autowired
    private BotService botService;
    @Autowired
    private BoardsReader boardsReader;
    @Autowired
    private GamePlay gamePlay;
    @Autowired
    private Sandbox sandbox;

    @Test
    public void should_start_gameplay() {
        //given
        UUID gameId = UUID.randomUUID();
        Board sampleBoard = provideBoard();
        List<BotAlgorithm> algorithms = provideBotAlgorithms();
        List<BotId> algorithmIds = algorithms.stream().map(BotAlgorithm::id).collect(Collectors.toList());
        when(gamePlay.startGame(sampleBoard, algorithms)).thenReturn(gameId);

        //when
        UUID startedGameId = sandbox.startNewGameplay(sampleBoard.getId(),algorithmIds);

        //then
        assertThat(startedGameId).isEqualTo(gameId);

    }

    private Board provideBoard() {
        Integer boardId = sandbox.getAvailableBoards().get(0);
        return boardsReader.getBoard(boardId);
    }

    private List<BotAlgorithm> provideBotAlgorithms() {
        List<BotId> availableBotIds = sandbox.getAvailableBots();
        return botService.fetch(availableBotIds);
    }
}