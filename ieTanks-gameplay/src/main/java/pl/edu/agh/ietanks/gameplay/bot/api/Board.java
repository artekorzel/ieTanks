package pl.edu.agh.ietanks.gameplay.bot.api;

public interface Board {

    String[] tankIds();

    Position findTank(String tankId);

    String findTankOnPosition(Position position);

    Missile[] findMissiles();

    Missile[] findMissilesOnPosition(Position position);

    boolean isWithin(Position position);

    boolean isAccessibleForTank(Position position);

}
