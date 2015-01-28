package pl.edu.agh.ietanks.ranking.api;

public class TankRanking {

    private String tankId;
    private int points;

    public TankRanking(String tankId, int points) {
        this.tankId = tankId;
        this.points = points;
    }

    public String getTankId() {
        return tankId;
    }

    public int getPoints() {
        return points;
    }
}
