package pl.edu.agh.ietanks.league.service;

import lombok.Value;
import lombok.experimental.Accessors;

import java.util.UUID;

@Value(staticConstructor = "of")
@Accessors(fluent = true)
public class LeagueId {
    UUID id;

    public static LeagueId of(String id) {
        return LeagueId.of(UUID.fromString(id));
    }
}
