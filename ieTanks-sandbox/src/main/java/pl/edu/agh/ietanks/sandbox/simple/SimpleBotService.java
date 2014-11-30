package pl.edu.agh.ietanks.sandbox.simple;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import pl.edu.agh.ietanks.gameplay.game.api.BotAlgorithm;
import pl.edu.agh.ietanks.gameplay.game.api.BotId;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;

import java.util.List;
import java.util.stream.Collectors;

@Service("simpleBotService")
public class SimpleBotService implements BotService {

    List<BotAlgorithm> algorithms = new ImmutableList.Builder<BotAlgorithm>().add(
            new BotAlgorithm(new BotId("some-bot"), "some-bot-code"),
            new BotAlgorithm(new BotId("some-other-bot"), "some-other-bot-code")
    ).build();

    @Override
    public List<BotId> listAvailableBots() {
        return algorithms.stream().map(BotAlgorithm::id).collect(Collectors.toList());
    }

    @Override
    public BotAlgorithm fetch(BotId botId) {
        return algorithms.stream().filter(bot -> bot.id() == botId).findFirst().orElse(null);
    }

    @Override
    public List<BotAlgorithm> fetch(List<BotId> botIds) {
        return algorithms.stream().filter(bot -> botIds.contains(bot.id())).collect(Collectors.toList());
    }
}
