package pl.edu.agh.ietanks.sandbox.rest.pojo;

import pl.edu.agh.ietanks.gameplay.game.api.BotId;


public class BotPojo {

    private String id;

    public BotPojo(BotId botId) {
        this.id = botId.id();
    }

    public String getId() {
        return id;
    }
}
