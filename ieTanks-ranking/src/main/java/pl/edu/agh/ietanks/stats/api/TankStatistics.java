package pl.edu.agh.ietanks.stats.api;

public class TankStatistics {

    private String tankId;
    private int tanksShot;
    private int bulletsShot;
    private int roundsPlayed;

    public TankStatistics(String tankId, int tanksShot, int bulletsShot, int roundsPlayed) {
        this.tankId = tankId;
        this.bulletsShot = bulletsShot;
        this.roundsPlayed = roundsPlayed;
        this.tanksShot = tanksShot;
    }

    public String getTankId() {
        return tankId;
    }

    public int getTanksShot() {
        return tanksShot;
    }

    public int getBulletsShot() {
        return bulletsShot;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }
}
