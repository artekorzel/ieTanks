package pl.edu.agh.ietanks.sandbox.rest.pojo;

/**
 * Created by adrian on 12/01/15.
 */
public class BotForm {
    private String botID;
    private String userID;
    private String code;

    public String getBotID() {
        return botID;
    }

    public void setBotID(String botID) {
        this.botID = botID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
