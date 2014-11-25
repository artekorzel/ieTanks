package pl.edu.agh.ietanks.gameplay.bot.api;

import com.google.common.base.Optional;

import java.util.Collection;
import java.util.List;

public interface Board {

    Integer[] tankIds();

    Position findTank(int tankId);

    Integer findTank(Position position);

    Missile[] findMissiles();

    Missile[] findMissiles(Position position);

    boolean isWithin(Position position);

    boolean isAccessibleForTank(Position position);

}