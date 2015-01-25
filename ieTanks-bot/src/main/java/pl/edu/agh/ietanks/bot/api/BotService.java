package pl.edu.agh.ietanks.bot.api;

import java.util.List;

public interface BotService {
    public List<BotId> listAvailableBots();

    public BotAlgorithm fetch(BotId botId);

    public List<BotAlgorithm> fetch(List<BotId> botIds);
}