package pl.edu.agh.ietanks.league.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import lombok.extern.java.Log;
import pl.edu.agh.ietanks.league.service.LeagueDefinition;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Log
public class LeagueDefinitionDTO {
    private final int gamesNumber;
    private final IntervalDTO interval;
    private final String firstGameDatetime;
    private final String boardId;
    private final List<String> players;

    @JsonCreator
    public LeagueDefinitionDTO(
            @JsonProperty("games_number") int gamesNumber,
            @JsonProperty("interval") IntervalDTO interval,
            @JsonProperty("first_game_datetime") String firstGameDatetime,
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

    public IntervalDTO interval() {
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

    public LeagueDefinition toLeagueDefinition() {
        return LeagueDefinition.builder()
                .gamesNumber(gamesNumber)
                .boardId(boardId)
                .players(ImmutableList.copyOf(players))
                .interval(interval.toInterval())
                .firstGameDatetime(ZonedDateTime.parse(firstGameDatetime, DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
