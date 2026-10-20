package fr.rally.competition.infrastructure.persistence;

import fr.rally.competition.application.PlayerRepository;
import fr.rally.competition.domain.Player;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class JpaPlayerRepository implements PlayerRepository {

    @NonNull
    private final SpringDataPlayerRepository repository;

    @Override
    public Player save(Player player) {
        PlayerJpaEntity entity = new PlayerJpaEntity(player.id(), player.displayName());
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Player> findById(UUID id) {
        return repository.findById(id).map(JpaPlayerRepository::toDomain);
    }

    private static Player toDomain(PlayerJpaEntity entity) {
        return new Player(entity.getId(), entity.getDisplayName());
    }
}
