package fr.rally.competition.application;

import fr.rally.competition.domain.Player;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    Player save(Player player);

    Optional<Player> findById(UUID id);
}
