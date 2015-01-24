package pl.edu.agh.ietanks.rank.api;

public class TankRank {

    private String tankId;
    private int points;

    public TankRank(String tankId) {
        this(tankId, 0);
    }

    public TankRank(String tankId, int points) {
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
