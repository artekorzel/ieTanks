package pl.edu.agh.ietanks.rank.api;

public class RankId {

    private String id;

    public RankId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RankId rankId = (RankId) o;
        return id.equals(rankId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "RankId{" +
                "id='" + id + '\'' +
                '}';
    }
}
