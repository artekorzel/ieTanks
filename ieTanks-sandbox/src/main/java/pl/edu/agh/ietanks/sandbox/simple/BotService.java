package pl.edu.agh.ietanks.sandbox.simple;

import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;

import java.util.List;

public interface BotService {
    public List<BotId> listAvailableBots();

    public BotAlgorithm fetch(BotId botId);

    public List<BotAlgorithm> fetch(List<BotId> botIds);
}