package pl.edu.agh.ietanks.ranking.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.agh.ietanks.ranking.api.RankingId;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class RankingNotFoundException extends RuntimeException {
    public RankingNotFoundException(RankingId rankingId) {
        super("Ranking with id: " + rankingId + " not found");
    }
}
