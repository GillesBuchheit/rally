package fr.rally.competition.infrastructure.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataLeagueRepository extends JpaRepository<LeagueJpaEntity, UUID> {
}
