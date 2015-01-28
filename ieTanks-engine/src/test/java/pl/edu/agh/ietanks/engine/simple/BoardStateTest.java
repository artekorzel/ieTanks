package pl.edu.agh.ietanks.engine.simple;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.Arrays;

import static org.fest.assertions.Assertions.assertThat;

public class BoardStateTest {

    BoardState boardState;
    private Position firstTankPosition;
    private Position secondTankPosition;
    private Position obstaclePosition;

    @Before
    public void setUp() {
        firstTankPosition = Position.topLeft();
        secondTankPosition = Position.topLeft().toRight(1);
        obstaclePosition = Position.topLeft().toDownRight(1);

        BoardDefinition boardDefinition = new BoardDefinition(3, 4, Arrays.asList(firstTankPosition, secondTankPosition),
                Arrays.asList(obstaclePosition));
        boardState = new BoardState(boardDefinition);

        boardState.placeTank("first-tank", firstTankPosition);
        boardState.placeTank("second-tank", secondTankPosition);
    }

    @Test
    public void isWithinShouldReturnTrueIfPositionIsInBoundariesButMayBeOccupiedByOtherTank() {
        // when-then
        assertThat(boardState.isWithin(firstTankPosition)).isTrue();
        assertThat(boardState.isWithin(secondTankPosition)).isTrue();
        assertThat(boardState.isWithin(secondTankPosition.toRight(1))).isTrue();
        assertThat(boardState.isWithin(Position.topLeft().toLeft(1))).isFalse();
    }

    @Test
    public void isAccessibleForTankShouldReturnTrueIfPositionIsInBoundariesButMayBeOccupiedByOtherTank() {
        // when-then
        assertThat(boardState.isAccessibleForTank(firstTankPosition)).isFalse();
        assertThat(boardState.isAccessibleForTank(secondTankPosition)).isFalse();
        assertThat(boardState.isAccessibleForTank(secondTankPosition.toRight(1))).isTrue();
        assertThat(boardState.isAccessibleForTank(Position.topLeft().toLeft(1))).isFalse();
        assertThat(boardState.isAccessibleForTank(obstaclePosition)).isFalse();
    }
}