package pl.edu.agh.ietanks.stats.api;

import pl.edu.agh.ietanks.gameplay.game.api.Game;

public interface StatCalculator {
    Statistics calculateStatForGame(Game game);
}
