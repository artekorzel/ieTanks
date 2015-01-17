package pl.edu.agh.ietanks.league.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public class LeagueDefinition {
    private final int gamesNumber;
    private final Interval interval;

    /**
     * For now as a string, later will change to DateTime (need object mapper configuration change) *
     */
    private final String firstGameDatetime;

    private final String boardId;
    private final List<String> players;

    @JsonCreator
    public LeagueDefinition(
            @JsonProperty("games_number") int gamesNumber,
            @JsonProperty("interval") Interval interval,
            @JsonProperty("first_game_datatime") String firstGameDatetime,
            @JsonProperty("board_id") String boardId,
            @JsonProperty("players") List<String> players) {

        this.gamesNumber = gamesNumber;
        this.interval = interval;
        this.firstGameDatetime = firstGameDatetime;
        this.boardId = boardId;
        this.players = Collections.unmodifiableList(players);
    }

    public int gamesNumber() {
        return gamesNumber;
    }

    public Interval interval() {
        return interval;
    }

    public String firstGameDatetime() {
        return firstGameDatetime;
    }

    public String boardId() {
        return boardId;
    }

    public List<String> players() {
        return players;
    }
}
