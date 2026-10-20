package fr.rally.competition.application;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

public final class CompetitionException extends RuntimeException {

    public enum Code {
        LEAGUE_NOT_FOUND,
        PLAYER_NOT_FOUND,
        PLAYER_ALREADY_REGISTERED,
        PLAYER_NOT_REGISTERED,
        NOT_LEAGUE_OWNER
    }

    @Getter
    @Accessors(fluent = true)
    private final Code code;

    public CompetitionException(@NonNull Code code) {
        super(code.name());
        this.code = code;
    }
}
