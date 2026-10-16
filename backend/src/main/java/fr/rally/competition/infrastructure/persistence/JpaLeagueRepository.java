package fr.rally.competition.infrastructure.persistence;

import fr.rally.competition.application.LeagueRepository;
import fr.rally.competition.domain.League;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
class JpaLeagueRepository implements LeagueRepository {

    private final SpringDataLeagueRepository repository;

    JpaLeagueRepository(SpringDataLeagueRepository repository) {
        this.repository = repository;
    }

    @Override
    public League save(League league) {
        LeagueJpaEntity entity = new LeagueJpaEntity(
                league.id(), league.name(), league.sport(), league.ownerId());
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<League> findById(UUID id) {
        return repository.findById(id).map(JpaLeagueRepository::toDomain);
    }

    private static League toDomain(LeagueJpaEntity entity) {
        return new League(entity.getId(), entity.getName(), entity.getSport(), entity.getOwnerId());
    }
}
