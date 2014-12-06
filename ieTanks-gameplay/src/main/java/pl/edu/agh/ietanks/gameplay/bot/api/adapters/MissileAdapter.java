package pl.edu.agh.ietanks.gameplay.bot.api.adapters;

import pl.edu.agh.ietanks.gameplay.bot.api.Missile;
import pl.edu.agh.ietanks.gameplay.bot.api.Position;
import pl.edu.agh.ietanks.gameplay.bot.api.ShotDirection;
import pl.edu.agh.ietanks.gameplay.bot.api.converters.ShotDirectionConverter;

public class MissileAdapter implements Missile {

    private pl.edu.agh.ietanks.engine.api.Missile engineMissile;
    private ShotDirectionConverter shotDirectionConverter;

    public MissileAdapter(pl.edu.agh.ietanks.engine.api.Missile engineMissile) {
        this.engineMissile = engineMissile;
        this.shotDirectionConverter = new ShotDirectionConverter();
    }

    @Override
    public ShotDirection direction() {
        return shotDirectionConverter.convertToGameplayDirection(engineMissile.direction());
    }

    @Override
    public Integer speed() {
        return engineMissile.speed();
    }

    @Override
    public Position position() {
        return new PositionAdapter(engineMissile.position());
    }
}
