package fr.rally.competition.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "league_registrations")
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class RegistrationJpaEntity {

    @EmbeddedId
    @NonNull
    private RegistrationJpaId id;

    @NonNull
    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;
}
