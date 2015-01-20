package pl.edu.agh.ietanks.league.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Interval {
    private final int value;
    private final String unit;

    @JsonCreator
    public Interval(@JsonProperty("value") int value,
                    @JsonProperty("unit") String unit) {
        this.value = value;
        this.unit = unit;
    }

    public int value() {
        return value;
    }

    public String unit() {
        return unit;
    }
}
