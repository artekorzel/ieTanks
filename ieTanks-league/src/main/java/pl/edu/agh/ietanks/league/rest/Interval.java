package pl.edu.agh.ietanks.league.rest;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Interval {
    private final int value;

    /**
     * Replace with TimeUnit later (conversion needed) *
     */
    private final String unit;

    @JsonCreator
    public Interval(int value, String unit) {
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
