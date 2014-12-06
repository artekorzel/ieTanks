package pl.edu.agh.ietanks.engine.simple;

import com.google.common.base.Optional;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankBumpedIntoWall;
import pl.edu.agh.ietanks.engine.api.events.TankMoved;
import pl.edu.agh.ietanks.engine.api.events.TankNotMoved;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;
import pl.edu.agh.ietanks.engine.testutils.BoardBuilder;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class GameLogicTest {
    @Test
    public void shouldMoveTankRightToAFreePlace() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 1), String.valueOf(0));

        // then
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "....",
                "..0.",
                "....",
                "...."))));

        assertThat(events).containsExactly(new TankMoved(String.valueOf(0), Direction.Right, 1));
    }

    @Test
    public void shouldNotMoveTank() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".01.",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 1), String.valueOf(0));

        // then
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "....",
                ".01.",
                "....",
                "...."))));

        assertThat(events).contains(new TankNotMoved(String.valueOf(0), Direction.Right, 1));
    }

    @Test
    public void shouldMoveTankToLastPlace() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 5), String.valueOf(0));

        // then
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "....",
                "...0",
                "....",
                "...."))));
        assertThat(events).contains(new TankMoved(String.valueOf(0), Direction.Right, 2));
        assertThat(events).contains(new TankBumpedIntoWall(String.valueOf(0), Direction.Right, 5));
    }

    @Test
    public void shouldMoveTankToLastFreePlaceBeforeTank() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "......",
                ".0.1..",
                "......",
                "......");

        GameLogic logic = new GameLogic(board);

        // when
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 5), String.valueOf(0));

        // then
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "......",
                "..01..",
                "......",
                "......"))));
        assertThat(events).contains(new TankMoved(String.valueOf(0), Direction.Right, 1));
    }

    @Test
    public void shouldCreateMissile() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        final List<Event> events = logic.tryApplyAction(new Shot(Direction.Down, 1), String.valueOf(0));

        // then
        assertThat(logic.board().findMissiles()).hasSize(1);
        assertThat(logic.board().findMissiles().iterator().next().position())
                .isEqualTo(new Position(2, 1));
        assertThat(events).hasSize(1);
    }

    @Test
    public void shouldMissileBeMoved() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down_Right, 1), String.valueOf(0));
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(1);
        assertThat(logic.board().findMissiles().iterator().next().position())
                .isEqualTo(new Position(3, 3));

    }

    @Test
    public void shouldMissilesBeDestroyed() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "0....",
                ".....",
                ".....",
                ".....",
                "....1");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down_Right, 1), String.valueOf(0));
        logic.tryApplyAction(new Shot(Direction.Up_Left, 1), String.valueOf(1));
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);

    }

    @Test
    public void shouldMissilesBeDestroyed2() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "0....",
                ".....",
                ".....",
                "...1.",
                ".....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down_Right, 1), String.valueOf(0));
        logic.moveMissiles();
        logic.tryApplyAction(new Shot(Direction.Up_Left, 1), String.valueOf(1));

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);

    }

    @Test
    public void shouldMissileBeRemoved() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Left, 1), String.valueOf(0));
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);

    }

    @Test
    public void shouldMissileAndTankBeRemoved() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                ".1..",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down, 1), String.valueOf(0));
        logic.tryApplyAction(new Move(Direction.Up, 1), String.valueOf(1));

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().findTank(String.valueOf(1))).isEqualTo(Optional.absent());
    }

    @Test
    public void shouldMissileAndTankBeRemoved2() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                ".1..",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down, 1), String.valueOf(0));
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....",
                "...."))));
    }

    @Test
    public void shouldMissileAndTankBeRemoved3() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0..",
                ".1..",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down, 1), String.valueOf(0));

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "....",
                ".0..",
                "....",
                "....",
                "...."))));
    }

    @Test
    public void shouldTankBeDestroyedWhileMoving() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                ".0..",
                "....",
                "....",
                "....",
                ".1..");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Down, 3), String.valueOf(0));
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Up, 3), String.valueOf(1));

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                ".0..",
                "....",
                "....",
                "....",
                "...."))));
        assertThat(events).contains(new TankMoved(String.valueOf(1), Direction.Up, 1));
    }

    @Test
    public void shouldTwoMissilesAndTankBeRemoved() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "0...1",
                ".....",
                "..2..",
                ".....",
                ".....");

        GameLogic logic = new GameLogic(board);

        // when
        logic.tryApplyAction(new Shot(Direction.Right, 1), String.valueOf(0));
        logic.tryApplyAction(new Shot(Direction.Left, 1), String.valueOf(1));
        logic.tryApplyAction(new Move(Direction.Up, 2), String.valueOf(2));
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().equals(new BoardState(BoardBuilder.fromASCII(
                "0...1",
                ".....",
                ".....",
                ".....",
                "....."))));
    }
}
