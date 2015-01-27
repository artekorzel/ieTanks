package pl.edu.agh.ietanks.bot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BotIdJson {

    private final String id;

    @JsonCreator
    public BotIdJson(@JsonProperty("bot_id") String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BotIdJson botId = (BotIdJson) o;

        if (id != null ? !id.equals(botId.id) : botId.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id='" + id + '\'' +
                '}';
    }
}