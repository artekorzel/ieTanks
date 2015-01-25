package pl.edu.agh.ietanks.ranking.api;

public class RankingId {

    private String id;

    public RankingId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RankingId rankingId = (RankingId) o;
        return id.equals(rankingId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "RankingId{" +
                "id='" + id + '\'' +
                '}';
    }
}
