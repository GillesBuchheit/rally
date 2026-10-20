package fr.rally.competition.domain;

import java.util.UUID;
import lombok.NonNull;

public record League(@NonNull UUID id, String name, @NonNull Sport sport, @NonNull UUID ownerId) {

    public League {
        name = normalizeName(name);
    }

    public static League create(String name, @NonNull Sport sport, @NonNull UUID ownerId) {
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
