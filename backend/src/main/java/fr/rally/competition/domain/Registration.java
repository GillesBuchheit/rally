package fr.rally.competition.domain;

import java.time.Instant;
import java.util.UUID;
import lombok.NonNull;

public record Registration(
        @NonNull UUID leagueId,
        @NonNull UUID playerId,
        @NonNull Instant joinedAt) {
}
