package fr.rally.competition.domain;

import java.util.Objects;
import java.util.UUID;

public record Player(UUID id, String displayName) {

    public Player {
        Objects.requireNonNull(id, "Player id is required");
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
