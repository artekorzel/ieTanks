package pl.edu.agh.ietanks.rank.api;

import java.util.Collections;
import java.util.List;

public class GameRank {

    private List<String> tankIds;

    public GameRank(List<String> tankIds) {
        this.tankIds = tankIds;
    }

    public List<String> getTankIds() {
        return Collections.unmodifiableList(tankIds);
    }
}
