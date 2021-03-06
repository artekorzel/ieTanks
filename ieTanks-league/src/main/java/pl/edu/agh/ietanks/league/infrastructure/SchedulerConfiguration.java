package pl.edu.agh.ietanks.league.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.league.external.UserService;
import pl.edu.agh.ietanks.league.service.LeagueRepository;
import pl.edu.agh.ietanks.league.service.LeagueService;
import pl.edu.agh.ietanks.ranking.api.RankingService;

@Configuration
public class SchedulerConfiguration {

    @Autowired
    private GamePlay gamePlayService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private UserService userService;

    @Bean
    public TaskScheduler createTaskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        return scheduler;
    }

    @Bean
    public LeagueRepository createLeagueRepository() {
        return new LeagueRepository();
    }

    @Bean
    public LeagueService createLeagueService() {
        return new LeagueService(createTaskScheduler(), userService,
                createLeagueRepository(), gamePlayService, rankingService);
    }
}
