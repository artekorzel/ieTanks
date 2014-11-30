package pl.edu.agh.ietanks.sandbox.simple;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.ietanks.boards.BoardsBean;
import pl.edu.agh.ietanks.boards.api.BoardsReader;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.BotService;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import static org.mockito.Mockito.mock;

@Configuration
public class SimpleSandboxTestContextConfiguration {
    private BotService botService = new SimpleBotService();
    private GamePlay gamePlay = mock(GamePlay.class);
    private BoardsReader boardsReader = new BoardsBean();
    private Sandbox sandBox = new SimpleSandbox(botService,boardsReader,gamePlay);

    @Bean
    public BotService botService() {
        return botService;
    }
    @Bean
    public GamePlay gamePlay() {
        return gamePlay;
    }
    @Bean
    public BoardsReader boardReader() {
        return boardsReader;
    }
    @Bean
    public Sandbox sandbox() {
        return sandBox;
    }
}
