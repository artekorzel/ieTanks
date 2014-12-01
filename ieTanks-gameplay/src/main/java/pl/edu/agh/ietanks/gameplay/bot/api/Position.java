package pl.edu.agh.ietanks.gameplay.bot.api;

public class Position {

    private Integer fromLeft;
    private Integer fromTop;

    public Position(Integer fromLeft, Integer fromTop) {
        this.fromLeft = fromLeft;
        this.fromTop = fromTop;
    }

    public Integer fromTop() {
        return fromTop;
    }

    public Integer fromLeft() {
        return fromLeft;
    }

}
