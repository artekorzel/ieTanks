package pl.edu.agh.ietanks.sandbox.simple;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.ietanks.gameplay.game.api.GamePlay;
import pl.edu.agh.ietanks.sandbox.simple.api.Sandbox;

import static org.mockito.Mockito.mock;

@Configuration
public class SimpleSandboxTestContextConfiguration {
    private GamePlay gamePlay = mock(GamePlay.class);
    private Sandbox sandBox = new SimpleSandbox(gamePlay);

    @Bean
    public GamePlay gamePlay() {
        return gamePlay;
    }

    @Bean
    public Sandbox sandbox() {
        return sandBox;
    }
}
