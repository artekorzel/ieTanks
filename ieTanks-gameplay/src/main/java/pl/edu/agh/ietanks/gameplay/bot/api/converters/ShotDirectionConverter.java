package pl.edu.agh.ietanks.gameplay.bot.api.converters;

import pl.edu.agh.ietanks.engine.api.GameplayBoardView;
import pl.edu.agh.ietanks.gameplay.bot.api.ShotDirection;

public class ShotDirectionConverter {

    public GameplayBoardView.Direction convertToEngineDirection(ShotDirection shotDirection) {
        switch (shotDirection) {
            case North:
                return GameplayBoardView.Direction.Up;
            case NorthEast:
                return GameplayBoardView.Direction.Up_Right;
            case East:
                return GameplayBoardView.Direction.Right;
            case SouthEast:
                return GameplayBoardView.Direction.Down_Right;
            case South:
                return GameplayBoardView.Direction.Down;
            case SouthWest:
                return GameplayBoardView.Direction.Down_Left;
            case West:
                return GameplayBoardView.Direction.Left;
            case NorthWest:
                return GameplayBoardView.Direction.Up_Left;
        }
        return null;
    }

    public ShotDirection convertToGameplayDirection(GameplayBoardView.Direction direction) {
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
