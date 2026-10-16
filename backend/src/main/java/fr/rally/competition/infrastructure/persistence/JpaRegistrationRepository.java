package fr.rally.competition.infrastructure.persistence;

import fr.rally.competition.application.CompetitionException;
import fr.rally.competition.application.RegistrationRepository;
import fr.rally.competition.domain.Registration;
import jakarta.persistence.EntityManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class JpaRegistrationRepository implements RegistrationRepository {

    private final SpringDataRegistrationRepository repository;
    private final EntityManager entityManager;

    JpaRegistrationRepository(SpringDataRegistrationRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Override
    public boolean exists(UUID leagueId, UUID playerId) {
        return repository.existsById(new RegistrationJpaId(leagueId, playerId));
    }

    @Override
    @Transactional
    public void insert(Registration registration) {
        RegistrationJpaId id = new RegistrationJpaId(registration.leagueId(), registration.playerId());
        try {
            entityManager.persist(new RegistrationJpaEntity(id, registration.joinedAt()));
            entityManager.flush();
        } catch (RuntimeException exception) {
            if (isUniqueViolation(exception)) {
                throw new CompetitionException(CompetitionException.Code.PLAYER_ALREADY_REGISTERED);
            }
            throw exception;
        }
    }

    @Override
    @Transactional
    public boolean delete(UUID leagueId, UUID playerId) {
        return repository.deleteRegistration(leagueId, playerId) > 0;
    }

    @Override
    public List<Registration> findByLeagueId(UUID leagueId) {
        return repository.findRegistrations(leagueId).stream()
                .map(entity -> new Registration(entity.getId().getLeagueId(),
                        entity.getId().getPlayerId(), entity.getJoinedAt()))
                .toList();
    }

    private static boolean isUniqueViolation(Throwable exception) {
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause instanceof SQLException sqlException && "23505".equals(sqlException.getSQLState())) {
                return true;
            }
        }
        return false;
    }
}
