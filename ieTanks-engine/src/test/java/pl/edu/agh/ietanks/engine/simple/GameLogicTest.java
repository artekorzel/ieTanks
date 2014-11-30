package pl.edu.agh.ietanks.engine.simple;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.base.Optional;

import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.engine.api.events.Event;
import pl.edu.agh.ietanks.engine.api.events.TankMoved;
import pl.edu.agh.ietanks.engine.api.events.TankNotMoved;
import pl.edu.agh.ietanks.engine.api.events.TankOutOfBoard;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;
import pl.edu.agh.ietanks.engine.testutils.BoardBuilder;

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
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 1), 0);

        // then
        assertThat(logic.board().findTank(0).get().equals(new Position(1, 2)));

        assertThat(events).containsExactly(new TankMoved(0, Direction.Right, 1));
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
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 1), 0);

        // then
        assertThat(logic.board().findTank(0).get().equals(new Position(1, 1)));
        assertThat(logic.board().findTank(1).get().equals(new Position(1, 2)));

        assertThat(events).contains(new TankNotMoved(0, Direction.Right, 1));
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
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 5), 0);

        // then
        assertThat(logic.board().findTank(0).get().equals(new Position(1, 3)));
        assertThat(events).contains(new TankOutOfBoard(0, Direction.Right, 5));
        assertThat(events).contains(new TankMoved(0, Direction.Right, 2));
    }

    @Test
    public void shouldNotMoveTankToLastPlace() throws Exception {
        //given
        BoardDefinition board = BoardBuilder.fromASCII(
                "....",
                ".0.1",
                "....",
                "....");

        GameLogic logic = new GameLogic(board);

        // when
        final List<Event> events = logic.tryApplyAction(new Move(Direction.Right, 5), 0);

        // then
        assertThat(logic.board().findTank(0).get().equals(new Position(1, 3)));
        assertThat(events).contains(new TankNotMoved(0, Direction.Right, 2));
        assertThat(events).contains(new TankOutOfBoard(0, Direction.Right, 5));
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
        final List<Event> events = logic.tryApplyAction(new Shot(Direction.Down, 1), 0);

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
        logic.tryApplyAction(new Shot(Direction.Down_Right, 1), 0);
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
        logic.tryApplyAction(new Shot(Direction.Down_Right, 1), 0);
        logic.tryApplyAction(new Shot(Direction.Up_Left, 1), 1);
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
        logic.tryApplyAction(new Shot(Direction.Down_Right, 1), 0);
        logic.moveMissiles();
        logic.tryApplyAction(new Shot(Direction.Up_Left, 1), 1);
        
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
        logic.tryApplyAction(new Shot(Direction.Left, 1), 0);
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
        logic.tryApplyAction(new Shot(Direction.Down, 1), 0);
        logic.tryApplyAction(new Move(Direction.Up, 1), 1);
        
        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().findTank(1)).isEqualTo(Optional.absent());
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
        logic.tryApplyAction(new Shot(Direction.Down, 1), 0);
        logic.moveMissiles();
        
        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().findTank(1)).isEqualTo(Optional.absent());
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
        logic.tryApplyAction(new Shot(Direction.Down, 1), 0);

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().findTank(1)).isEqualTo(Optional.absent());
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
        logic.tryApplyAction(new Shot(Direction.Right, 1), 0);
        logic.tryApplyAction(new Shot(Direction.Left, 1), 1);
        logic.tryApplyAction(new Move(Direction.Up, 2), 2);
        logic.moveMissiles();

        // then
        assertThat(logic.board().findMissiles()).hasSize(0);
        assertThat(logic.board().findTank(2)).isEqualTo(Optional.absent());
    }
}