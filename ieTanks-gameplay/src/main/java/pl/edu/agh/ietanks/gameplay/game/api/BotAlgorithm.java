package pl.edu.agh.ietanks.gameplay.game.api;

public class BotAlgorithm {

    private final BotId id;
    private final String pythonCode;

    public BotAlgorithm(BotId id, String pythonCode) {
        this.id = id;
        this.pythonCode = pythonCode;
    }

    public BotId id() {
        return id;
    }

    public String pythonCode() {
        return pythonCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BotAlgorithm that = (BotAlgorithm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pythonCode != null ? !pythonCode.equals(that.pythonCode) : that.pythonCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pythonCode != null ? pythonCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BotAlgorithm{" +
                "id='" + id + '\'' +
                ", pythonCode='" + pythonCode + '\'' +
                '}';
    }
}
