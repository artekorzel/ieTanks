package pl.edu.agh.ietanks.league.service;

import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;
import org.python.google.common.collect.Maps;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LeagueState {
    private final LeagueId id;
    private final String authorId;

    private Map<RoundId, Round> rounds = Maps.newConcurrentMap();
    private AtomicInteger counter = new AtomicInteger(0);

    public LeagueState(LeagueId id, String authorId) {
        this.id = id;
        this.authorId = authorId;
    }

    private static Date toDate(ZonedDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

    public LeagueId id() {
        return id;
    }

    public RoundId createRound(ZonedDateTime scheduledDate) {
        RoundId id = new RoundId(counter.incrementAndGet());
        rounds.put(id, Round.builder()
                .roundState(RoundState.SCHEDULED)
                .scheduledDate(scheduledDate)
                .build());

        return id;
    }

    public void startRound(RoundId roundId) {
        final Round round = rounds.get(roundId);
        rounds.put(roundId, round.start());
    }

    public void finishRound(RoundId roundId) {
        final Round round = rounds.get(roundId);
        rounds.put(roundId, round.finish());
    }

    public League toLeague() {
        final int playedGamesNumber = (int) rounds.values().stream()
                .filter(state -> state.roundState().equals(RoundState.FINISHED))
                .count();
        final int allGamesNumber = rounds.keySet().size();

        final ZonedDateTime nextScheduledGameTime = rounds.values().stream()
                .filter(state -> state.roundState().equals(RoundState.SCHEDULED))
                .map(Round::scheduledDate)
                .sorted((date1, date2) -> date1.toInstant().compareTo(date2.toInstant()))
                .findFirst()
                .orElse(null);

        return League.builder()
                .allGames(allGamesNumber)
                .playedGames(playedGamesNumber)
                .authorId(this.authorId)
                .id(this.id)
                .isActive(allGamesNumber != playedGamesNumber)
                .nextScheduledGame(nextScheduledGameTime)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeagueState)) return false;

        LeagueState that = (LeagueState) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    private static enum RoundState {
        SCHEDULED, STARTED, FINISHED
    }

    @Value
    @Accessors(fluent = true)
    public static class RoundId {
        int id;
    }

    @Value
    @Builder
    @Accessors(fluent = true)
    private static class Round {
        RoundState roundState;
        ZonedDateTime scheduledDate;

        public Round start() {
            return Round.builder().scheduledDate(scheduledDate).roundState(RoundState.STARTED).build();
        }

        public Round finish() {
            return Round.builder().scheduledDate(scheduledDate).roundState(RoundState.FINISHED).build();
        }
    }
}
