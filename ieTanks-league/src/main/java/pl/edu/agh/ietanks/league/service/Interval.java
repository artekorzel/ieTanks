package pl.edu.agh.ietanks.league.service;

import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Builder;

import java.util.concurrent.TimeUnit;

@Builder
@Value
@Accessors(fluent = true)
public class Interval {
    private final int value;
    private final TimeUnit unit;
}
