package fr.rally.competition.infrastructure.persistence;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface SpringDataRegistrationRepository extends JpaRepository<RegistrationJpaEntity, RegistrationJpaId> {

    @Modifying
    @Query("delete from RegistrationJpaEntity r where r.id.leagueId = :leagueId and r.id.playerId = :playerId")
    int deleteRegistration(@Param("leagueId") UUID leagueId, @Param("playerId") UUID playerId);

    @Query("select r from RegistrationJpaEntity r where r.id.leagueId = :leagueId order by r.joinedAt")
    List<RegistrationJpaEntity> findRegistrations(@Param("leagueId") UUID leagueId);
}
