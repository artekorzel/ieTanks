package pl.edu.agh.ietanks.gameplay.bot.api;

public abstract class Bot {

    private String id;

    protected Bot(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract Action performAction(Board board);

    public Position getMyPosition(Board board) {
        return board.findTank(id);
    }
}
