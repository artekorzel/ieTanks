package pl.edu.agh.ietanks.league.service;

import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import java.time.ZonedDateTime;

@Builder
@Value
@Accessors(fluent = true)
public class League {
    private final LeagueId id;
    private final boolean isActive;
    private final String authorId;
    private final ZonedDateTime nextScheduledGame;
    private final int allGames;
    private final int playedGames;
}
