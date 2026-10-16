package fr.rally.competition.application;

import fr.rally.competition.domain.League;
import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository {
    League save(League league);

    Optional<League> findById(UUID id);
}
