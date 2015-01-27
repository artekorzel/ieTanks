package pl.edu.agh.ietanks.stats.api;

public class TankStatistics {

    private String tankId;
    private int bulletsShot;
    private int tankMoves;
    private int roundsPlayed;

    public TankStatistics(String tankId, int bulletsShot, int tankMoves, int roundsPlayed) {
        this.tankId = tankId;
        this.bulletsShot = bulletsShot;
        this.roundsPlayed = roundsPlayed;
        this.tankMoves = tankMoves;
    }

    public void incrementBulletsShot(){
        bulletsShot++;
    }

    public String getTankId() {
        return tankId;
    }

    public int getTanksShot() {
        return tankMoves;
    }

    public int getBulletsShot() {
        return bulletsShot;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }
}
