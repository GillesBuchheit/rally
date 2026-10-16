package fr.rally.competition.application;

import fr.rally.competition.domain.League;
import fr.rally.competition.domain.Player;
import fr.rally.competition.domain.Registration;
import fr.rally.competition.domain.Sport;
import java.time.Clock;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static fr.rally.competition.application.CompetitionException.Code.*;

public final class CompetitionService {

    private final LeagueRepository leagues;
    private final PlayerRepository players;
    private final RegistrationRepository registrations;
    private final Clock clock;

    public CompetitionService(LeagueRepository leagues, PlayerRepository players,
                              RegistrationRepository registrations, Clock clock) {
        this.leagues = Objects.requireNonNull(leagues);
        this.players = Objects.requireNonNull(players);
        this.registrations = Objects.requireNonNull(registrations);
        this.clock = Objects.requireNonNull(clock);
    }

    public League createLeague(String name, Sport sport, UUID ownerId) {
        return leagues.save(League.create(name, sport, ownerId));
    }

    public Player createPlayer(String displayName) {
        return players.save(Player.create(displayName));
    }

    public Registration registerPlayer(UUID leagueId, UUID playerId, UUID actorId) {
        requireOwner(leagueId, actorId);
        players.findById(playerId).orElseThrow(() -> new CompetitionException(PLAYER_NOT_FOUND));
        if (registrations.exists(leagueId, playerId)) {
            throw new CompetitionException(PLAYER_ALREADY_REGISTERED);
        }
        Registration registration = new Registration(leagueId, playerId, Instant.now(clock));
        registrations.insert(registration);
        return registration;
    }

    public void removePlayer(UUID leagueId, UUID playerId, UUID actorId) {
        requireOwner(leagueId, actorId);
        if (!registrations.delete(leagueId, playerId)) {
            throw new CompetitionException(PLAYER_NOT_REGISTERED);
        }
    }

    public List<Player> listPlayers(UUID leagueId) {
        requireLeague(leagueId);
        return registrations.findByLeagueId(leagueId).stream()
                .map(registration -> players.findById(registration.playerId())
                        .orElseThrow(() -> new CompetitionException(PLAYER_NOT_FOUND)))
                .sorted(Comparator.comparing(Player::displayName).thenComparing(Player::id))
                .toList();
    }

    private void requireOwner(UUID leagueId, UUID actorId) {
        Objects.requireNonNull(actorId, "Actor id is required");
        if (!requireLeague(leagueId).ownerId().equals(actorId)) {
            throw new CompetitionException(NOT_LEAGUE_OWNER);
        }
    }

    private League requireLeague(UUID leagueId) {
        return leagues.findById(leagueId)
                .orElseThrow(() -> new CompetitionException(LEAGUE_NOT_FOUND));
    }
}
