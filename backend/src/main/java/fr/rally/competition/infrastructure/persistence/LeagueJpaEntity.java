package fr.rally.competition.infrastructure.persistence;

import fr.rally.competition.domain.Sport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "leagues")
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class LeagueJpaEntity {

    @Id
    @NonNull
    private UUID id;

    @NonNull
    @Column(nullable = false, length = 120)
    private String name;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Sport sport;

    @NonNull
    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;
}
