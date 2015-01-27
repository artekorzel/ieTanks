package pl.edu.agh.ietanks.stats.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.agh.ietanks.stats.api.StatId;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class StatNotFoundException extends RuntimeException {
    public StatNotFoundException(StatId statId) {
        super("Stat with id: " + statId + " not found");
    }
}
