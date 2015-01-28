package pl.edu.agh.ietanks.gameplay.bot.api.converters;

import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.gameplay.bot.api.MoveDirection;

public class MoveDirectionConverter {

    public Direction convertToEngineDirection(MoveDirection moveDirection) {
        switch (moveDirection) {
            case North:
                return Direction.Up;
            case East:
                return Direction.Right;
            case South:
                return Direction.Down;
            case West:
                return Direction.Left;
        }
        return null;
    }
}
