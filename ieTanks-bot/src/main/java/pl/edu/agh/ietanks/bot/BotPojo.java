package pl.edu.agh.ietanks.bot;

import pl.edu.agh.ietanks.bot.api.BotId;


public class BotPojo {

    private String id;

    public BotPojo(BotId botId) {
        this.id = botId.id();
    }

    public String getId() {
        return id;
    }
}
