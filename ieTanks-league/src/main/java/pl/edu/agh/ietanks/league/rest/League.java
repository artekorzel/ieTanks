package pl.edu.agh.ietanks.league.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class League {
    private final String id;
    private final boolean isActive;
    private final String authorId;
    private final String nextScheduledGame;
    private final int allGames;
    private final int playedGames;

    public League(String id, boolean isActive, String authorId, String nextScheduledGame, int allGames, int playedGames) {
        this.id = id;
        this.isActive = isActive;
        this.authorId = authorId;
        this.nextScheduledGame = nextScheduledGame;
        this.allGames = allGames;
        this.playedGames = playedGames;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("is_active")
    public boolean isActive() {
        return isActive;
    }

    @JsonProperty("author_id")
    public String getAuthorId() {
        return authorId;
    }

    @JsonProperty("next_scheduled_game")
    public String getNextScheduledGame() {
        return nextScheduledGame;
    }

    @JsonProperty("all_games")
    public int getAllGames() {
        return allGames;
    }

    @JsonProperty("played_games")
    public int getPlayedGames() {
        return playedGames;
    }
}
