package fr.rally.competition.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "players")
class PlayerJpaEntity {

    @Id
    private UUID id;

    @Column(name = "display_name", nullable = false, length = 120)
    private String displayName;

    protected PlayerJpaEntity() {
    }

    PlayerJpaEntity(UUID id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    UUID getId() {
        return id;
    }

    String getDisplayName() {
        return displayName;
    }
}
