package fr.rally.competition.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Registration(UUID leagueId, UUID playerId, Instant joinedAt) {

    public Registration {
        Objects.requireNonNull(leagueId, "League id is required");
        Objects.requireNonNull(playerId, "Player id is required");
        Objects.requireNonNull(joinedAt, "Registration time is required");
    }
}
