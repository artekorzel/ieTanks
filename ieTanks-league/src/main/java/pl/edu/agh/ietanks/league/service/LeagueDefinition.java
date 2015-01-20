package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.ImmutableList;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import java.time.LocalDateTime;

@Builder
@Value
@Accessors(fluent = true)
public class LeagueDefinition {
    private final int gamesNumber;
    private final Interval interval;
    private final LocalDateTime firstGameDatetime;
    private final String boardId;
    private final ImmutableList<String> players;
}
