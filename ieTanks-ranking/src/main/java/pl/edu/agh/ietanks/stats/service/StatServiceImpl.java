package pl.edu.agh.ietanks.stats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.gameplay.game.api.Game;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.stats.api.*;
import pl.edu.agh.ietanks.stats.exceptions.GameNotFoundException;
import pl.edu.agh.ietanks.stats.exceptions.StatNotFoundException;

import java.util.Optional;

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    GameHistory gameHistory;

    @Autowired
    StatStorage statStorage;

    @Autowired
    StatsMerger statsMerger;

    @Autowired
    StatCalculator statCalculator;

    @Override
    public Optional<Statistics> findStatById(StatId id) {
        return statStorage.findStatById(id);
    }

    @Override
    public StatId createStat() {
        return statStorage.createStat();
    }

    @Override
    public void addGameToStat(StatId statId, GameId gameId) {
        Optional<Statistics> statOptional = statStorage.findStatById(statId);
        if (!statOptional.isPresent()) {
            throw new StatNotFoundException(statId);
        }

        Optional<Game> gameOptional = gameHistory.getGame(gameId);
        if (!gameOptional.isPresent()) {
            throw new GameNotFoundException(gameId);
        }

        Statistics gameStat = statCalculator.calculateStatForGame(gameOptional.get());
        Statistics stat = statsMerger.apply(statOptional.get(), gameStat);
        statStorage.updateStat(stat);
    }
}
