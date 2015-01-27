package pl.edu.agh.ietanks.ranking.internal.api;

import pl.edu.agh.ietanks.gameplay.game.api.Game;

public interface RankingCalculator {
    Ranking calculateRankingForGame(Game game);
}
