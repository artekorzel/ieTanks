package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.fest.assertions.Condition;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.*;
import pl.edu.agh.ietanks.engine.api.events.*;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;
import pl.edu.agh.ietanks.engine.testutils.BoardBuilder;
import pl.edu.agh.ietanks.engine.testutils.BotBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

public class SimpleEngineTest {

    private static final String TANK_0 = "0";
    private static final String TANK_1 = "1";
    private static final String TANK_2 = "2";

    private final EngineFactory factory = new SimpleEngineFactory();

    @Test
    public void shouldMoveTanksInTurns() throws Exception {
        // given
        BoardDefinition startingBoard = BoardBuilder.fromASCII(
                "....",
                "x...",
                "..x.",
                "....");

        Bot bot1 = BotBuilder.fromSequence(TANK_0, new Move(Direction.Right, 1));
        Bot bot2 = BotBuilder.fromSequence(TANK_1, new Move(Direction.Right, 1));

        Engine engine = factory.createEngineInstance(
                startingBoard, Lists.newArrayList(bot1, bot2), GameConfig.defaults());

        // when
        final List<Event> events1 = engine.nextMove().getRoundEvents();
        final List<Event> events2 = engine.nextMove().getRoundEvents();

        // then
        final ArrayList<Event> allEvents = Lists.newArrayList(Iterables.concat(events1, events2));

        Condition<List<?>> containsElements = new Condition<List<?>>() {
            @Override
            public boolean matches(List<?> events) {
                if (events.contains(new TankMoved(TANK_0, Direction.Right, 1, new Position(2,3))) &&
                        events.contains(new TankMoved(TANK_1, Direction.Right, 1, new Position(1,1)))) {
                    return true;
                }
                if (events.contains(new TankMoved(TANK_0, Direction.Right, 1, new Position(1,1))) &&
                        events.contains(new TankMoved(TANK_1, Direction.Right, 1, new Position(2,3)))) {
                    return true;
                }
                return false;
            }
        };
        assertThat(allEvents).satisfies(containsElements);
    }

    @Test
    public void shouldLimitNumbersOfMoves() {
        // given
        BoardDefinition startingBoard = BoardBuilder.fromASCII(
                ".....",
                ".x...",
                "...x.",
                ".....");

        Bot bot1 = BotBuilder.fromSequence(TANK_0, new Move(Direction.Right, 1), new Move(Direction.Left, 1));
        Bot bot2 = BotBuilder.fromSequence(TANK_1, new Move(Direction.Up, 1), new Move(Direction.Down, 1));

        Engine engine = factory.createEngineInstance(startingBoard, Lists.newArrayList(bot1, bot2),
                     GameConfig.newBuilder().withTurnsLimit(4).createGameConfig());

        for(int i=0; i<3; ++i) {
            engine.nextMove();
        }

        // when
        final RoundResults roundResults = engine.nextMove();

        // then
        assertThat(roundResults.isGameFinished()).isTrue();
    }

    @Test
    public void shouldFinishGameWhenLastTankLeft() {
        // given
        BoardDefinition startingBoard = BoardBuilder.fromASCII(
                ".....",
                ".....",
                ".....",
                "x.x.x");

        Bot bot1 = BotBuilder.fromSequence(TANK_0, new Shot(Direction.Right, 1), new Shot(Direction.Right, 1));
        Bot bot2 = BotBuilder.fromSequence(TANK_1, new Shot(Direction.Right, 1), new Shot(Direction.Right, 1));
        Bot bot3 = BotBuilder.fromSequence(TANK_2, new Shot(Direction.Right, 1), new Shot(Direction.Right, 1));

        Engine engine = factory.createEngineInstance(
                startingBoard, Lists.newArrayList(bot1, bot2, bot3), GameConfig.defaults());

        String firstTank = engine.currentBoard().findTank(Position.topLeft().toDown(3));

        // when

        List<Event> events = Lists.newArrayList();

        for (int i = 0; i < 10; ++i) {
            RoundResults result = engine.nextMove();
            events.addAll(result.getRoundEvents());

            if (result.isGameFinished()) break;
        }

        // then

        assertThat(events).contains(new GameFinished(Optional.of(firstTank)));
    }
}
