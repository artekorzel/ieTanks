package pl.edu.agh.ietanks.engine.simple;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.Action;
import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.engine.api.events.*;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;
import pl.edu.agh.ietanks.engine.testutils.StateBuilder;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class GameLogicTest {
    private String TANK_0 = "0";
    private String TANK_1 = "1";
    private String TANK_2 = "2";

    private GameLogic logic;
    private List<Event> events;

    @Before
    public void setUp() {
        events = Lists.newArrayList();
    }

    private void given(String... boardAscii) {
        BoardState board = StateBuilder.fromASCII(boardAscii);
        logic = new GameLogic(board);
    }

    private void when(Action action, String tankId) {
        events.addAll(logic.tryApplyAction(action, tankId));
    }

    private void then(String... boardAscii) {
        assertThat(logic.board().equals(StateBuilder.fromASCII(boardAscii)));
    }

    private void expectedEvents(Event... expectedEvents) {
        assertThat(events).contains(expectedEvents);
    }

    @Test
    public void shouldMoveTankRightToAFreePlace() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                "....");

        when(new Move(Direction.Right, 1), TANK_0);

        then(
                "....",
                "..0.",
                "....",
                "....");

        expectedEvents(
                new TankMoved(TANK_0, Direction.Right, 1, new Position(1, 2)));
    }

    @Test
    public void shouldNotMoveTank() throws Exception {
        given(
                "....",
                ".01.",
                "....",
                "....");

        when(new Move(Direction.Right, 1), TANK_0);

        then(
                "....",
                ".01.",
                "....",
                "....");

        expectedEvents(
                new TankNotMoved(TANK_0, Direction.Right, 1, new Position(1,1)));
    }

    @Test
    public void shouldMoveTankToLastPlace() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                "....");

        when(new Move(Direction.Right, 5), TANK_0);

        then(
                "....",
                "...0",
                "....",
                "....");

        expectedEvents(
                new TankMoved(TANK_0, Direction.Right, 2, new Position(1, 3)),
                new TankBumpedIntoWall(TANK_0, Direction.Right, 5, new Position(1, 3)));
    }

    @Test
    public void shouldMoveTankToLastFreePlaceBeforeTank() throws Exception {
        given(
                "......",
                ".0.1..",
                "......",
                "......");

        when(new Move(Direction.Right, 5), TANK_0);

        then(
                "......",
                "..01..",
                "......",
                "......");

        expectedEvents(
                new TankMoved(TANK_0, Direction.Right, 1, new Position(1, 2)));
    }

    @Test
    public void shouldCreateMissile() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                "....");

        when(new Shot(Direction.Down, 1), TANK_0);

        // then
        assertThat(logic.board().findMissiles()).hasSize(1);
        assertThat(logic.board().findMissiles().iterator().next().position())
                .isEqualTo(new Position(2, 1));
        assertThat(events).hasSize(1);
    }

    @Test
    public void shouldMissileBeMoved() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                "....");

        when(new Shot(Direction.Down_Right, 1), TANK_0);
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(1);
        assertThat(logic.board().findMissiles().iterator().next().position())
                .isEqualTo(new Position(3, 3));

    }

    @Test
    public void shouldMissilesBeDestroyed() throws Exception {
        given(
                "0....",
                ".....",
                ".....",
                ".....",
                "....1");

        when(new Shot(Direction.Down_Right, 1), TANK_0);
        when(new Shot(Direction.Up_Left, 1), TANK_1);
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldMissilesBeDestroyed2() throws Exception {
        //given
        given(
                "0....",
                ".....",
                ".....",
                "...1.",
                ".....");

        when(new Shot(Direction.Down_Right, 1), TANK_0);
        logic.moveMissiles();
        when(new Shot(Direction.Up_Left, 1), TANK_1);

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldMissileBeRemoved() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                "....",
                "....");

        when(new Shot(Direction.Left, 1), TANK_0);
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldMissileBeRemovedJustAfterCreation() throws Exception {
        given(
                "....",
                "0...",
                "....",
                "....",
                "....");

        when(new Shot(Direction.Left, 1), TANK_0);

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldMissileAndTankBeRemoved() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                ".1..",
                "....");

        when(new Shot(Direction.Down, 1), TANK_0);
        when(new Move(Direction.Up, 1), TANK_1);

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().findTank(TANK_1)).isEqualTo(Optional.absent());
    }

    @Test
    public void shouldMissileAndTankBeRemoved2() throws Exception {
        given(
                "....",
                ".0..",
                "....",
                ".1..",
                "....");

        when(new Shot(Direction.Down, 1), TANK_0);
        logic.moveMissiles();

        then(
                "....",
                ".0..",
                "....",
                "....",
                "....");
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldMissileAndTankBeRemoved3() throws Exception {
        given(
                "....",
                ".0..",
                ".1..",
                "....",
                "....");

        when(new Shot(Direction.Down, 1), TANK_0);

        then(
                "....",
                ".0..",
                "....",
                "....",
                "....");
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldTankBeDestroyedWhileMoving() throws Exception {
        given(
                ".0..",
                "....",
                "....",
                "....",
                ".1..");

        when(new Shot(Direction.Down, 3), TANK_0);
        when(new Move(Direction.Up, 3), TANK_1);

        then(
                ".0..",
                "....",
                "....",
                "....",
                "....");
        assertThat(logic.board().findMissiles()).hasSize(0);

        expectedEvents(
                new TankMoved(TANK_1, Direction.Up, 1, new Position(3,1)),
                new TankDestroyed(TANK_1, new Position(3,1))
        );
    }

    @Test
    public void shouldTwoMissilesAndTankBeRemoved() throws Exception {
        given(
                "0...1",
                ".....",
                "..2..",
                ".....",
                ".....");


        when(new Shot(Direction.Right, 1), TANK_0);
        when(new Shot(Direction.Left, 1), TANK_1);
        when(new Move(Direction.Up, 2), TANK_2);
        logic.moveMissiles();

        then(
                "0...1",
                ".....",
                ".....",
                ".....",
                ".....");
        assertThat(logic.board().findMissiles()).hasSize(0);
    }

    @Test
    public void shouldTankNotMoveToObstacle() throws Exception {
        given(
                "0.#.",
                "....",
                "....",
                "....");

        when(new Move(Direction.Right, 3), TANK_0);

        then(
                ".0#.",
                "....",
                "....",
                "....");

        expectedEvents(
                new TankMoved(TANK_0, Direction.Right, 1, new Position(0, 1)));
    }
}
