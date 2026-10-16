package fr.rally.competition.domain;

import java.util.Objects;
import java.util.UUID;

public record League(UUID id, String name, Sport sport, UUID ownerId) {

    public League {
        Objects.requireNonNull(id, "League id is required");
        Objects.requireNonNull(sport, "Sport is required");
        Objects.requireNonNull(ownerId, "Owner id is required");
        name = normalizeName(name);
    }

    public static League create(String name, Sport sport, UUID ownerId) {
        return new League(UUID.randomUUID(), name, sport, ownerId);
    }

    private static String normalizeName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("League name is required");
        }
        String normalized = name.strip();
        if (normalized.length() > 120) {
            throw new IllegalArgumentException("League name must contain at most 120 characters");
        }
        return normalized;
    }
}
