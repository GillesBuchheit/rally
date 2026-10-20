package fr.rally.competition.domain;

import java.util.UUID;
import lombok.NonNull;

public record Player(@NonNull UUID id, String displayName) {

    public Player {
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Player name is required");
        }
        displayName = displayName.strip();
        if (displayName.length() > 120) {
            throw new IllegalArgumentException("Player name must contain at most 120 characters");
        }
    }

    public static Player create(String displayName) {
        return new Player(UUID.randomUUID(), displayName);
    }
}
