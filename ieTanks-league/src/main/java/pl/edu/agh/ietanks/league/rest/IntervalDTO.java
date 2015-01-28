package pl.edu.agh.ietanks.league.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.edu.agh.ietanks.league.service.Interval;

import java.util.concurrent.TimeUnit;

public class IntervalDTO {
    private final int value;
    private final String unit;

    @JsonCreator
    public IntervalDTO(@JsonProperty("value") int value,
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

    public Interval toInterval() {
        return Interval.builder()
                .unit(TimeUnit.valueOf(unit.toUpperCase()))
                .value(value)
                .build();
    }
}
