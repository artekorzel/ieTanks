package pl.edu.agh.ietanks.rank.api;

import pl.edu.agh.ietanks.gameplay.game.api.GameId;

import java.util.Optional;

public interface GameRankService {
    Optional<GameRank> getRankForGame(GameId gameId);
}
