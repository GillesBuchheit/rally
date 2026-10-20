package fr.rally.competition.application;

import fr.rally.competition.domain.Registration;
import java.util.List;
import java.util.UUID;

public interface RegistrationRepository {
    boolean exists(UUID leagueId, UUID playerId);

    void insert(Registration registration);

    boolean delete(UUID leagueId, UUID playerId);

    List<Registration> findByLeagueId(UUID leagueId);
}
