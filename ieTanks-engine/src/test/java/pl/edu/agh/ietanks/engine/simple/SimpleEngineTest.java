package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Lists;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.RoundResults;
import pl.edu.agh.ietanks.engine.api.events.TankMoved;
import pl.edu.agh.ietanks.engine.api.events.TankNotMoved;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.testutils.BoardBuilder;
import pl.edu.agh.ietanks.engine.testutils.BotBuilder;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SimpleEngineTest {
    @Test
    public void shouldMoveTanksInTurns() throws Exception {
        // given
        BoardDefinition startingBoard = BoardBuilder.fromASCII(
                "....",
                "01..",
                "....",
                "....");

        Bot bot1 = BotBuilder.fromSequence(String.valueOf(0), new Move(Direction.Right, 1));
        Bot bot2 = BotBuilder.fromSequence(String.valueOf(1), new Move(Direction.Right, 1));

        Engine engine = new SimpleEngine();
        engine.setup(startingBoard, Lists.newArrayList(bot1, bot2), GameConfig.defaults());

        // when
        final List<Event> events1 = engine.nextMove().getRoundEvents();

        // then
        assertThat(engine.currentBoard()).isEqualTo(new BoardState(startingBoard));
        assertThat(events1).containsExactly(new TankNotMoved(String.valueOf(0), Direction.Right, 1));

        // when
        final List<Event> events2 = engine.nextMove().getRoundEvents();

        // then
        assertThat(engine.currentBoard()).isEqualTo(new BoardState(BoardBuilder.fromASCII(
                "....",
                "0.1.",
                "....",
                "...."
        )));
        assertThat(events2).containsExactly(new TankMoved(String.valueOf(1), Direction.Right, 1));
    }

    @Test
    public void shouldLimitNumbersOfMoves() {
        // given
        BoardDefinition startingBoard = BoardBuilder.fromASCII(
                ".....",
                ".0...",
                "...1.",
                ".....");

        Bot bot1 = BotBuilder.fromSequence(String.valueOf(0), new Move(Direction.Right, 1), new Move(Direction.Left, 1));
        Bot bot2 = BotBuilder.fromSequence(String.valueOf(1), new Move(Direction.Up, 1), new Move(Direction.Down, 1));

        Engine engine = new SimpleEngine();
        engine.setup(startingBoard, Lists.newArrayList(bot1, bot2),
                     GameConfig.newBuilder().withTurnsLimit(4).createGameConfig());

        for(int i=0; i<3; ++i) {
            engine.nextMove();
        }

        // when
        final RoundResults roundResults = engine.nextMove();

        // then
        assertThat(roundResults.isGameFinished()).isTrue();
    }
}
