package pl.edu.agh.ietanks.rank.api;

import pl.edu.agh.ietanks.gameplay.game.api.Game;

import java.util.List;
import java.util.Optional;

public interface GameRankService {

    Optional<GameRank> getRank(RankId id);

    GameRank calculateRankForGame(Game game);

    GameRank calculateRankForGames(List<Game> games);
}
