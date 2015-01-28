package pl.edu.agh.ietanks.gameplay.bot.api.adapters;

import pl.edu.agh.ietanks.gameplay.bot.api.Position;

public class PositionAdapter extends Position {

    public PositionAdapter(pl.edu.agh.ietanks.engine.api.Position enginePosition) {
        super(enginePosition.fromLeft(), enginePosition.fromTop());
    }

}
