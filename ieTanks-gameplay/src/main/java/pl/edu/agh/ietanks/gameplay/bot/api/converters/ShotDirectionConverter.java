package pl.edu.agh.ietanks.gameplay.bot.api.converters;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.gameplay.bot.api.ShotDirection;

public class ShotDirectionConverter {

    public Direction convertToEngineDirection(ShotDirection shotDirection) {
        switch (shotDirection) {
            case North:
                return Direction.Up;
            case NorthEast:
                return Direction.Up_Right;
            case East:
                return Direction.Right;
            case SouthEast:
                return Direction.Down_Right;
            case South:
                return Direction.Down;
            case SouthWest:
                return Direction.Down_Left;
            case West:
                return Direction.Left;
            case NorthWest:
                return Direction.Up_Left;
        }
        return null;
    }

    public ShotDirection convertToGameplayDirection(Direction direction) {
        switch (direction) {
            case Right:
                return ShotDirection.East;
            case Left:
                return ShotDirection.West;
            case Up:
                return ShotDirection.North;
            case Down:
                return ShotDirection.South;
            case Up_Right:
                return ShotDirection.NorthEast;
            case Up_Left:
                return ShotDirection.NorthWest;
            case Down_Right:
                return ShotDirection.SouthEast;
            case Down_Left:
                return ShotDirection.SouthWest;
        }
        return null;
    }
}
