package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Lists;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Bot;
import pl.edu.agh.ietanks.engine.api.Engine;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankMoved;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.testutils.BoardBuilder;
import pl.edu.agh.ietanks.engine.testutils.BotBuilder;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SimpleEngineTest {
    @Test
    public void shouldMoveTanksInTurns() throws Exception {
        // given
        BoardDefinition boardDefinition = BoardBuilder.fromASCII(
                "....",
                "01..",
                "....",
                "....");
        BoardState boardState = new BoardState(boardDefinition);

        Bot bot1 = BotBuilder.fromSequence("0", new Move(GameplayBoardView.Direction.Right, 1));
        Bot bot2 = BotBuilder.fromSequence("1", new Move(GameplayBoardView.Direction.Right, 1));

        Engine engine = new SimpleEngine();
        engine.setup(boardDefinition, Lists.newArrayList(bot1, bot2));

        // when
        final List<Event> events1 = engine.nextMove().getRoundEvents();

        // then
        assertThat(engine.currentBoard()).isEqualTo(boardState);
        assertThat(events1).isEmpty();

        // when
        final List<Event> events2 = engine.nextMove().getRoundEvents();

        // then
        BoardState expectedBoard = new BoardState(BoardBuilder.fromASCII(
                "....",
                "0.1.",
                "....",
                "...."));
        assertThat(engine.currentBoard()).isEqualTo(expectedBoard);
        assertThat(events2).containsExactly(new TankMoved("1", GameplayBoardView.Direction.Right, 1));
    }
}