package pl.edu.agh.ietanks.ranking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.agh.ietanks.gameplay.game.api.GameId;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(GameId gameId) {
        super("Game with id: " + gameId + " not found");
    }
}

