package pl.edu.agh.ietanks.gameplay.bot.api.converters;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.gameplay.bot.api.MoveDirection;

public class MoveDirectionConverter {

    public GameplayBoardView.Direction convertToEngineDirection(MoveDirection moveDirection) {
        switch (moveDirection) {
            case North:
                return GameplayBoardView.Direction.Up;
            case East:
                return GameplayBoardView.Direction.Right;
            case South:
                return GameplayBoardView.Direction.Down;
            case West:
                return GameplayBoardView.Direction.Left;
        }
        return null;
    }
}
