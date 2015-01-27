package pl.edu.agh.ietanks.stats.api;

public class StatId {

    private String id;

    public StatId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatId statId = (StatId) o;
        return id.equals(statId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "StatId{" +
                "id='" + id + '\'' +
                '}';
    }
}
