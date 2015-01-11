package pl.edu.agh.ietanks.rank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.gameplay.game.api.GameHistory;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;
import pl.edu.agh.ietanks.rank.api.GameRank;
import pl.edu.agh.ietanks.rank.api.GameRankService;

import java.util.Optional;

@Service
public class SurvivorGameRankService implements GameRankService {

    @Autowired
    GameHistory gameHistory;

    @Override
    public Optional<GameRank> getRankForGame(GameId gameId) {
        return null;
    }
}
