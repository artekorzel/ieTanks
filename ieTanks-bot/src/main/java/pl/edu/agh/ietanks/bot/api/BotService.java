package pl.edu.agh.ietanks.bot.api;

import pl.edu.agh.ietanks.bot.BotForm;

import java.util.List;

public interface BotService {
    public List<BotId> listAvailableBots();

    public void saveBot(BotForm botForm);

    public BotAlgorithm fetch(BotId botId);

    public List<BotAlgorithm> fetch(List<BotId> botIds);
}