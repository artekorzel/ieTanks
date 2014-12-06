package pl.edu.agh.ietanks.engine.simple;

import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;

import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class BoardStateTest {

    private Position firstTankPosition;
    private Position secondTankPosition;
    Map<String, Position> initialTankPositions;
    BoardState boardState;

    @Before
    public void setUp() {
        firstTankPosition = Position.topLeft();
        secondTankPosition = Position.topLeft().toRight(1);

        initialTankPositions = Maps.newHashMap();
        initialTankPositions.put("first-tank", firstTankPosition);
        initialTankPositions.put("second-tank", secondTankPosition);

        BoardDefinition boardDefinition = new BoardDefinition(3, 4, initialTankPositions);
        boardState = new BoardState(boardDefinition);
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
    }
}