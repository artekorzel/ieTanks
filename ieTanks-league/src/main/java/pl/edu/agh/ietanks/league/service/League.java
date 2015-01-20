package pl.edu.agh.ietanks.league.service;

import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import java.time.LocalDateTime;

@Builder
@Value
@Accessors(fluent = true)
public class League {
    private final String id;
    private final boolean isActive;
    private final String authorId;
    private final LocalDateTime nextScheduledGame;
    private final int allGames;
    private final int playedGames;
}
