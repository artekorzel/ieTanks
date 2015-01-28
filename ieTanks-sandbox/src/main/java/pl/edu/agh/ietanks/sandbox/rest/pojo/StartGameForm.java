package pl.edu.agh.ietanks.sandbox.rest.pojo;


import java.util.List;

public class StartGameForm {

    private List<String> botIds;
    private int boardId;

    public List<String> getBotIds() {
        return botIds;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBotIds(List<String> botIds) {
        this.botIds = botIds;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }
}
