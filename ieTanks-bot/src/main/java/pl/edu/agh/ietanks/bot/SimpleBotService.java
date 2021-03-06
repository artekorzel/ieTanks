package pl.edu.agh.ietanks.bot;

import com.google.common.collect.ImmutableList;
import pl.edu.agh.ieTanks.utils.ResourceUtils;
import pl.edu.agh.ietanks.bot.api.BotAlgorithm;
import pl.edu.agh.ietanks.bot.api.BotId;
import pl.edu.agh.ietanks.bot.api.BotService;

import java.util.List;
import java.util.stream.Collectors;

/*@Service("simpleBotService")*/
public class SimpleBotService implements BotService {

    private final String fixedBotAlgorithm = ResourceUtils.loadResourceFromFile("TestBot.py");

    List<BotAlgorithm> algorithms = new ImmutableList.Builder<BotAlgorithm>().add(
            new BotAlgorithm(new BotId("some-bot"), fixedBotAlgorithm),
            new BotAlgorithm(new BotId("some-other-bot"), fixedBotAlgorithm)
    ).build();

    @Override
    public List<BotId> listAvailableBots() {
        return algorithms.stream().map(BotAlgorithm::id).collect(Collectors.toList());
    }

    @Override
    public void saveBot(BotForm botForm) {

    }

    @Override
    public BotAlgorithm fetch(BotId botId) {
        return algorithms.stream().filter(bot -> bot.id().equals(botId)).findFirst().orElse(null);
    }

    @Override
    public List<BotAlgorithm> fetch(List<BotId> botIds) {
        return algorithms.stream().filter(bot -> botIds.contains(bot.id())).collect(Collectors.toList());
    }
}
