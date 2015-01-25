package pl.edu.agh.ietanks.ranking.api;

import pl.edu.agh.ietanks.gameplay.game.api.Game;

public interface RankingCalculator {
    Ranking calculateRankingForGame(Game game);
}
