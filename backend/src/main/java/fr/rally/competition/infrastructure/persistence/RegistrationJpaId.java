package fr.rally.competition.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Embeddable
@Getter(AccessLevel.PACKAGE)
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class RegistrationJpaId implements Serializable {

    @NonNull
    @Column(name = "league_id", nullable = false)
    private UUID leagueId;

    @NonNull
    @Column(name = "player_id", nullable = false)
    private UUID playerId;
}
