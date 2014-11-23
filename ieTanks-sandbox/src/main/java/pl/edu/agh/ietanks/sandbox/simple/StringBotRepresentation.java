package pl.edu.agh.ietanks.sandbox.simple;

public class StringBotRepresentation {

    private final BotId id;

    private final String botSourceCode;

    public StringBotRepresentation(BotId id, String botSourceCode) {
        this.id = id;
        this.botSourceCode = botSourceCode;
    }

    public BotId id() {
        return id;
    }

    public String botSourceCode() {
        return botSourceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringBotRepresentation that = (StringBotRepresentation) o;

        if (botSourceCode != null ? !botSourceCode.equals(that.botSourceCode) : that.botSourceCode != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (botSourceCode != null ? botSourceCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StringBotRepresentation{" +
                "id=" + id +
                ", botSourceCode='" + botSourceCode + '\'' +
                '}';
    }
}
