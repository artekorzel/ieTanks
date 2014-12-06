package pl.edu.agh.ietanks.gameplay;

import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.simple.BoardState;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;
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
        String filename = "SampleBot.py";

        //when
        String result = ResourceUtils.loadResourceFromFile(filename);

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void should_return_simple_up_left_move() throws IOException {
        //given
        String pythonAlgorithm = ResourceUtils.loadResourceFromFile("SampleBot.py");
        BotId id = new BotId("1");
        BotExecutor underTest = new BotExecutor(id, pythonAlgorithm);
        HashMap map = new HashMap<Integer, Position>();
        map.put("1", new Position(5, 5));
        map.put("2", new Position(4, 4));
        // TODO: used class outside of API!
        BoardDefinition boardDefinition = new BoardDefinition(20, 20, map);
        GameplayBoardView board = new BoardState(boardDefinition);
        //GameplayBoardView board = null;

        //when
        Action resultAction = underTest.performAction(board);

        //then
        assertThat(resultAction).isInstanceOf(Action.class);
        assertThat(resultAction).isEqualTo(new Shot(Direction.Up_Left, 1));
    }

}
