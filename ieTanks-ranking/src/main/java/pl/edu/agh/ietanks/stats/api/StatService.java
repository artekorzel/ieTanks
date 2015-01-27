package pl.edu.agh.ietanks.stats.api;

import pl.edu.agh.ietanks.gameplay.game.api.GameId;

import java.util.Optional;

public interface StatService {

    Optional<Statistics> findStatById(StatId rankingId);

    StatId createStat();

    void addGameToStat(StatId statId, GameId gameId);
}
