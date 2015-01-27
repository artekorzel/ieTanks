package pl.edu.agh.ietanks.league.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

public class LeagueRepositoryTest {

    private static final LeagueId LEAGUE_ID = LeagueId.of(UUID.randomUUID());
    private static final String AUTHOR_ID = "mequrel";

    private LeagueRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new LeagueRepository();
    }

    @Test
    public void shouldReturnSavedLeagues() throws Exception {
        // when
        final LeagueState league1 = repository.createLeague(AUTHOR_ID);
        final LeagueState league2 = repository.createLeague(AUTHOR_ID);

        // then
        final Collection<LeagueState> leagues = repository.fetchAll();
        assertThat(leagues).containsOnly(league1, league2);

        final Optional<LeagueState> fetchedLeague1 = repository.fetchLeague(league1.id());
        final Optional<LeagueState> expectedLeague1 = Optional.of(league1);
        assertThat(fetchedLeague1).isEqualTo(expectedLeague1);

        final Optional<LeagueState> fetchedLeague2 = repository.fetchLeague(league2.id());
        final Optional<LeagueState> expectedLeague2 = Optional.of(league2);
        assertThat(fetchedLeague2).isEqualTo(expectedLeague2);
    }

    @Test
    public void shouldReturnEmptyWhenThereIsGivenLeague() throws Exception {
        // when
        final Optional<LeagueState> league1 = repository.fetchLeague(LEAGUE_ID);

        // then
        assertThat(league1.isPresent()).isFalse();
    }
}