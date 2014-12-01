package pl.edu.agh.ietanks.gameplay.board;

import com.google.common.base.Preconditions;
import pl.edu.agh.ietanks.boards.model.Board;
import pl.edu.agh.ietanks.engine.api.BoardDefinition;
import pl.edu.agh.ietanks.engine.api.Position;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;

import java.util.*;

public class BoardDefinitionAdapter implements BoardDefinition {
    private final Board board;
    private final Map<String, Position> tanks = new HashMap<>();

    public BoardDefinitionAdapter(Board board, List<BotId> botIds) {
        Preconditions.checkArgument(botIds.size() <= 5, "More than 5 tanks not supported yet.");

        this.board = board;

        List<Position> goodPositions = new ArrayList<>();
        goodPositions.add(Position.topLeft());
        goodPositions.add(Position.topLeft().toRight(width() - 1).toDown(height() - 1));
        goodPositions.add(Position.topLeft().toRight(width() - 1));
        goodPositions.add(Position.topLeft().toDown(height() - 1));
        goodPositions.add(Position.topLeft().toRight((width() - 1) / 2).toDown((height() - 1) / 2));

        int i = 0;
        for (BotId id : botIds) {
            tanks.put(id.id(), goodPositions.get(i));
            i++;
        }
    }

    @Override
    public int width() {
        return board.getWidth();
    }

    @Override
    public int height() {
        return board.getWidth();
    }

    @Override
    public Map<String, Position> initialTankPositions() {
        return Collections.unmodifiableMap(tanks);
    }
}
