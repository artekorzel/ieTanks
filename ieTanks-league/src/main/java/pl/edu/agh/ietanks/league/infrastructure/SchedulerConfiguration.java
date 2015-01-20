package pl.edu.agh.ietanks.league.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import pl.edu.agh.ietanks.boards.BoardsBean;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.league.service.LeagueService;
import pl.edu.agh.ietanks.league.service.RankingService;
import pl.edu.agh.ietanks.league.service.RoundExecutorFactory;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;

@Configuration
public class SchedulerConfiguration {

    @Autowired
    private BoardsBean boardService;

    @Autowired
    //@Qualifier("httpBotService")
    @Qualifier("simpleBotService")
    private BotService botService;

    @Autowired
    private GamePlay gamePlayService;

    @Autowired
    private RankingService rankingService;

    @Bean
    public TaskScheduler createTaskScheduler() {
        final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        return scheduler;
    }

    @Bean
    public RoundExecutorFactory createExecutorFactory() {
        return new RoundExecutorFactory(boardService, botService, rankingService, gamePlayService);
    }

    @Bean
    public LeagueService createLeagueService() {
        return new LeagueService(createTaskScheduler(), createExecutorFactory());
    }
}
