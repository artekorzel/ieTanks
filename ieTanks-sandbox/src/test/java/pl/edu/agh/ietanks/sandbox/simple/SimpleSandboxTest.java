package pl.edu.agh.ietanks.sandbox.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
        classes = SimpleSandboxTestContextConfiguration.class)
public class SimpleSandboxTest {

    @Autowired
    private GamePlay gamePlay;
    @Autowired
    private Sandbox sandbox;

    @Test
    public void shouldStartGameplay() {
        //given
        GameId gameId = new GameId(UUID.randomUUID().toString());
        int boardId = 1;
        List<BotId> algorithmIds = Arrays.asList(new BotId("some-bot"), new BotId("some-other-bot"));
        when(gamePlay.startNewGameplay(eq(1), eq(algorithmIds), any())).thenReturn(gameId);

        //when
        GameId startedGameId = sandbox.startNewGameplay(boardId, algorithmIds);

        //then
        assertThat(startedGameId).isEqualTo(gameId);

    }
}