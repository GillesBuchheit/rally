package fr.rally.competition.infrastructure.persistence;

import fr.rally.competition.domain.Sport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "leagues")
class LeagueJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Sport sport;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    protected LeagueJpaEntity() {
    }

    LeagueJpaEntity(UUID id, String name, Sport sport, UUID ownerId) {
        this.id = id;
        this.name = name;
        this.sport = sport;
        this.ownerId = ownerId;
    }

    UUID getId() {
        return id;
    }

    String getName() {
        return name;
    }

    Sport getSport() {
        return sport;
    }

    UUID getOwnerId() {
        return ownerId;
    }
}
