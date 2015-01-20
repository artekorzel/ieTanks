package pl.edu.agh.ietanks.league.service;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LeagueServiceTest {

    private static final Interval EVERY_TWO_SECONDS = Interval.builder()
            .value(2)
            .unit(TimeUnit.SECONDS)
            .build();
    private static final LeagueDefinition LEAGUE_DEFINITION = LeagueDefinition.builder()
            .gamesNumber(1)
            .interval(EVERY_TWO_SECONDS)
            .firstGameDatetime(LocalDateTime.now())
            .players(ImmutableList.of("mequrel", "adebski"))
            .build();
    private static final String ONE_USER_TO_RULE_THEM_ALL = "mequrel";

    @InjectMocks
    private LeagueService leagueService;

    @Test
    public void shouldListStartedLeagues() throws Exception {
        LeagueId leagueId = leagueService.startLeague(LEAGUE_DEFINITION);

        Thread.sleep(2000);

        final Collection<League> leagues = leagueService.fetchAll();
        assertThat(leagues).hasSize(1);
        checkLeague(leagueId, leagues.iterator().next());

        final Optional<League> fetchedLeague = leagueService.fetchLeague(leagueId);
        assertThat(fetchedLeague.isPresent()).isTrue();

        final League league = fetchedLeague.get();
        checkLeague(leagueId, league);
    }

    private void checkLeague(LeagueId leagueId, League league) {
        assertThat(league.authorId()).isEqualTo(ONE_USER_TO_RULE_THEM_ALL);
        assertThat(league.id()).isEqualTo(leagueId.toString());
        assertThat(league.isActive()).isFalse();
        assertThat(league.playedGames()).isEqualTo(1);
        assertThat(league.allGames()).isEqualTo(1);
    }
}
