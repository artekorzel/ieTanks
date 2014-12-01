package pl.edu.agh.ietanks.gameplay;

import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.Action;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.gameplay.bot.BotExecutor;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.gameplay.testutils.ResourceUtils;

import java.io.IOException;
import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;

public class BotExecutorTest {

    @Test
    public void should_load_resource_correctly() throws IOException {
        //given
        String filename = "TestBot.py";

        //when
        String result = ResourceUtils.loadResourceFromFile(filename);

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void should_return_simple_up_move() throws IOException {
        //given
        String pythonAlgorithm = ResourceUtils.loadResourceFromFile("TestBot.py");
        BotId id = new BotId("1");
        BotExecutor underTest = new BotExecutor(id, pythonAlgorithm);
        HashMap map = new HashMap<Integer, Position>();
        map.put(id, new Position(2, 2));
        // TODO: used class outside of API!
        //GameplayBoardView board = new BoardState(3, 3, map);
        GameplayBoardView board = null;

        //when
        Action resultAction = underTest.performAction(board);

        //then
        assertThat(resultAction).isInstanceOf(Action.class);
        assertThat((Action) resultAction).isEqualTo(new Move(GameplayBoardView.Direction.Right, 1));
    }

}
