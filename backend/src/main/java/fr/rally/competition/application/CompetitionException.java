package fr.rally.competition.application;

public final class CompetitionException extends RuntimeException {

    public enum Code {
        LEAGUE_NOT_FOUND,
        PLAYER_NOT_FOUND,
        PLAYER_ALREADY_REGISTERED,
        PLAYER_NOT_REGISTERED,
        NOT_LEAGUE_OWNER
    }

    private final Code code;

    public CompetitionException(Code code) {
        super(code.name());
        this.code = code;
    }

    public Code code() {
        return code;
    }
}
