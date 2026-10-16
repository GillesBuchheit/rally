package fr.rally.competition.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "league_registrations")
class RegistrationJpaEntity {

    @EmbeddedId
    private RegistrationJpaId id;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    protected RegistrationJpaEntity() {
    }

    RegistrationJpaEntity(RegistrationJpaId id, Instant joinedAt) {
        this.id = id;
        this.joinedAt = joinedAt;
    }

    RegistrationJpaId getId() {
        return id;
    }

    Instant getJoinedAt() {
        return joinedAt;
    }
}
