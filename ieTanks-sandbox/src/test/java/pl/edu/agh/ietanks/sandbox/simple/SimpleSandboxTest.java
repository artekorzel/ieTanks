package pl.edu.agh.ietanks.sandbox.simple;

import com.google.common.collect.Lists;
import org.junit.Test;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleSandboxTest {

    private BotService botService = mock(BotService.class);

    private BoardsReader boardsReader = mock(BoardsReader.class);

    private GamePlay gamePlay = mock(GamePlay.class);

    private SimpleSandbox simpleSandbox = new SimpleSandbox(botService, boardsReader, gamePlay);

    @Test
    public void shouldStartGameplay() {
        // given
        UUID gameId = UUID.randomUUID();
        int boardId = 5;
        Board sampleBoard = new Board(5, "some-board", 1, 1, Collections.emptyList());
        BotId firstBotId = new BotId("some-bot");
        BotId secondBotId = new BotId("some-other-bot");
        List<BotAlgorithm> algorithms = Lists.newArrayList(
                new BotAlgorithm(firstBotId, "some-bot-code"),
                new BotAlgorithm(secondBotId, "some-other-bot-code")
        );

        // when
        when(botService.fetch(Lists.newArrayList(firstBotId, secondBotId))).thenReturn(algorithms);
        when(boardsReader.getBoard(boardId)).thenReturn(sampleBoard);
        when(gamePlay.startGame(sampleBoard, algorithms)).thenReturn(gameId);

        // then
        assertThat(simpleSandbox.startNewGameplay(boardId, Lists.newArrayList(firstBotId, secondBotId))).isEqualTo(gameId);
    }
}