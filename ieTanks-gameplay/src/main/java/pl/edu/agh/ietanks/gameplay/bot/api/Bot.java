package pl.edu.agh.ietanks.gameplay.bot.api;

public abstract class Bot {

    private Integer id;

    protected Bot(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public abstract Action performAction(Board board);

    public Position getMyPosition(Board board) {
        return board.findTank(id);
    }
}
