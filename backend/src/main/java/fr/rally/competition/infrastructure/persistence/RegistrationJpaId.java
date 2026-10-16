package fr.rally.competition.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
class RegistrationJpaId implements Serializable {

    @Column(name = "league_id", nullable = false)
    private UUID leagueId;

    @Column(name = "player_id", nullable = false)
    private UUID playerId;

    protected RegistrationJpaId() {
    }

    RegistrationJpaId(UUID leagueId, UUID playerId) {
        this.leagueId = leagueId;
        this.playerId = playerId;
    }

    UUID getLeagueId() {
        return leagueId;
    }

    UUID getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RegistrationJpaId that)) {
            return false;
        }
        return Objects.equals(leagueId, that.leagueId)
                && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leagueId, playerId);
    }
}
