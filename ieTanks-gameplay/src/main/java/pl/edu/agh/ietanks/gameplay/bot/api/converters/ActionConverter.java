package pl.edu.agh.ietanks.gameplay.bot.api.converters;

import pl.edu.agh.ietanks.engine.api.Action;
import pl.edu.agh.ietanks.engine.api.Direction;
import pl.edu.agh.ietanks.engine.simple.actions.Move;
import pl.edu.agh.ietanks.engine.simple.actions.NoOperation;
import pl.edu.agh.ietanks.engine.simple.actions.Shot;

public class ActionConverter {

    private MoveDirectionConverter moveDirectionConverter;
    private ShotDirectionConverter shotDirectionConverter;

    public ActionConverter() {
        moveDirectionConverter = new MoveDirectionConverter();
        shotDirectionConverter = new ShotDirectionConverter();
    }

    public Action convertToEngineAction(pl.edu.agh.ietanks.gameplay.bot.api.Action action) {
        if (action instanceof pl.edu.agh.ietanks.gameplay.bot.api.Nothing) {
            return new NoOperation();
        }
        if (action instanceof pl.edu.agh.ietanks.gameplay.bot.api.Move) {
            pl.edu.agh.ietanks.gameplay.bot.api.Move moveAction = (pl.edu.agh.ietanks.gameplay.bot.api.Move) action;
            Direction direction = moveDirectionConverter.convertToEngineDirection(moveAction.direction());
            return new Move(direction, 1);
        }
        if (action instanceof pl.edu.agh.ietanks.gameplay.bot.api.Shot) {
            pl.edu.agh.ietanks.gameplay.bot.api.Shot shotAction = (pl.edu.agh.ietanks.gameplay.bot.api.Shot) action;
            Direction direction = shotDirectionConverter.convertToEngineDirection(shotAction.direction());
            return new Shot(direction, 1);
        }
        return null;
    }
}
