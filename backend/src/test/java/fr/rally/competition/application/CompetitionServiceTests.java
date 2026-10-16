package fr.rally.competition.application;

import fr.rally.competition.domain.League;
import fr.rally.competition.domain.Player;
import fr.rally.competition.domain.Registration;
import fr.rally.competition.domain.Sport;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.rally.competition.application.CompetitionException.Code.LEAGUE_NOT_FOUND;
import static fr.rally.competition.application.CompetitionException.Code.NOT_LEAGUE_OWNER;
import static fr.rally.competition.application.CompetitionException.Code.PLAYER_ALREADY_REGISTERED;
import static fr.rally.competition.application.CompetitionException.Code.PLAYER_NOT_FOUND;
import static fr.rally.competition.application.CompetitionException.Code.PLAYER_NOT_REGISTERED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompetitionServiceTests {

    private static final Instant NOW = Instant.parse("2026-10-16T09:00:00Z");

    private InMemoryLeagueRepository leagues;
    private InMemoryPlayerRepository players;
    private InMemoryRegistrationRepository registrations;
    private CompetitionService competition;

    @BeforeEach
    void setUp() {
        leagues = new InMemoryLeagueRepository();
        players = new InMemoryPlayerRepository();
        registrations = new InMemoryRegistrationRepository();
        competition = new CompetitionService(
                leagues,
                players,
                registrations,
                Clock.fixed(NOW, ZoneOffset.UTC));
    }

    @Test
    void createsNormalizedLeagueAndPlayer() {
        UUID ownerId = UUID.randomUUID();

        League league = competition.createLeague("  Ligue du dimanche  ", Sport.TENNIS, ownerId);
        Player player = competition.createPlayer("  Camille Martin  ");

        assertThat(league.name()).isEqualTo("Ligue du dimanche");
        assertThat(league.ownerId()).isEqualTo(ownerId);
        assertThat(player.displayName()).isEqualTo("Camille Martin");
        assertThat(leagues.findById(league.id())).contains(league);
        assertThat(players.findById(player.id())).contains(player);
    }

    @Test
    void allowsAPlayerInSeveralLeagues() {
        UUID ownerId = UUID.randomUUID();
        Player player = competition.createPlayer("Camille");
        League tennis = competition.createLeague("Tennis", Sport.TENNIS, ownerId);
        League padel = competition.createLeague("Padel", Sport.PADEL, ownerId);

        competition.registerPlayer(tennis.id(), player.id(), ownerId);
        competition.registerPlayer(padel.id(), player.id(), ownerId);

        assertThat(registrations.findByLeagueId(tennis.id())).hasSize(1);
        assertThat(registrations.findByLeagueId(padel.id())).hasSize(1);
    }

    @Test
    void rejectsASecondRegistrationInTheSameLeague() {
        UUID ownerId = UUID.randomUUID();
        Player player = competition.createPlayer("Camille");
        League league = competition.createLeague("Open", Sport.TENNIS, ownerId);
        competition.registerPlayer(league.id(), player.id(), ownerId);

        assertCode(
                () -> competition.registerPlayer(league.id(), player.id(), ownerId),
                PLAYER_ALREADY_REGISTERED);
    }

    @Test
    void recordsRegistrationTimeFromTheInjectedClock() {
        UUID ownerId = UUID.randomUUID();
        Player player = competition.createPlayer("Camille");
        League league = competition.createLeague("Open", Sport.PADEL, ownerId);

        Registration registration = competition.registerPlayer(league.id(), player.id(), ownerId);

        assertThat(registration.joinedAt()).isEqualTo(NOW);
    }

    @Test
    void rejectsRegistrationBySomeoneOtherThanTheOwner() {
        UUID ownerId = UUID.randomUUID();
        Player player = competition.createPlayer("Camille");
        League league = competition.createLeague("Open", Sport.TENNIS, ownerId);

        assertCode(
                () -> competition.registerPlayer(league.id(), player.id(), UUID.randomUUID()),
                NOT_LEAGUE_OWNER);
        assertThat(registrations.findByLeagueId(league.id())).isEmpty();
    }

    @Test
    void rejectsUnknownLeagueAndPlayer() {
        UUID ownerId = UUID.randomUUID();
        League league = competition.createLeague("Open", Sport.TENNIS, ownerId);

        assertCode(
                () -> competition.registerPlayer(UUID.randomUUID(), UUID.randomUUID(), ownerId),
                LEAGUE_NOT_FOUND);
        assertCode(
                () -> competition.registerPlayer(league.id(), UUID.randomUUID(), ownerId),
                PLAYER_NOT_FOUND);
    }

    @Test
    void removesARegisteredPlayerAndRejectsASecondRemoval() {
        UUID ownerId = UUID.randomUUID();
        Player player = competition.createPlayer("Camille");
        League league = competition.createLeague("Open", Sport.TENNIS, ownerId);
        competition.registerPlayer(league.id(), player.id(), ownerId);

        competition.removePlayer(league.id(), player.id(), ownerId);

        assertThat(competition.listPlayers(league.id())).isEmpty();
        assertCode(
                () -> competition.removePlayer(league.id(), player.id(), ownerId),
                PLAYER_NOT_REGISTERED);
    }

    @Test
    void listsPlayersInAStableAlphabeticalOrder() {
        UUID ownerId = UUID.randomUUID();
        League league = competition.createLeague("Open", Sport.TENNIS, ownerId);
        Player zoe = competition.createPlayer("Zoé");
        Player alice = competition.createPlayer("Alice");
        competition.registerPlayer(league.id(), zoe.id(), ownerId);
        competition.registerPlayer(league.id(), alice.id(), ownerId);

        assertThat(competition.listPlayers(league.id()))
                .extracting(Player::displayName)
                .containsExactly("Alice", "Zoé");
    }

    @Test
    void rejectsBlankAndOverlongNames() {
        assertThatThrownBy(() -> competition.createPlayer("   "))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> competition.createLeague("x".repeat(121), Sport.PADEL, UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static void assertCode(Runnable action, CompetitionException.Code expectedCode) {
        assertThatThrownBy(action::run)
                .isInstanceOf(CompetitionException.class)
                .satisfies(error -> assertThat(((CompetitionException) error).code())
                        .isEqualTo(expectedCode));
    }

    private static final class InMemoryLeagueRepository implements LeagueRepository {
        private final Map<UUID, League> entries = new LinkedHashMap<>();

        @Override
        public League save(League league) {
            entries.put(league.id(), league);
            return league;
        }

        @Override
        public Optional<League> findById(UUID id) {
            return Optional.ofNullable(entries.get(id));
        }
    }

    private static final class InMemoryPlayerRepository implements PlayerRepository {
        private final Map<UUID, Player> entries = new LinkedHashMap<>();

        @Override
        public Player save(Player player) {
            entries.put(player.id(), player);
            return player;
        }

        @Override
        public Optional<Player> findById(UUID id) {
            return Optional.ofNullable(entries.get(id));
        }
    }

    private static final class InMemoryRegistrationRepository implements RegistrationRepository {
        private final Map<RegistrationKey, Registration> entries = new LinkedHashMap<>();

        @Override
        public boolean exists(UUID leagueId, UUID playerId) {
            return entries.containsKey(new RegistrationKey(leagueId, playerId));
        }

        @Override
        public void insert(Registration registration) {
            RegistrationKey key = new RegistrationKey(registration.leagueId(), registration.playerId());
            if (entries.putIfAbsent(key, registration) != null) {
                throw new CompetitionException(PLAYER_ALREADY_REGISTERED);
            }
        }

        @Override
        public boolean delete(UUID leagueId, UUID playerId) {
            return entries.remove(new RegistrationKey(leagueId, playerId)) != null;
        }

        @Override
        public List<Registration> findByLeagueId(UUID leagueId) {
            List<Registration> result = new ArrayList<>();
            entries.values().stream()
                    .filter(registration -> registration.leagueId().equals(leagueId))
                    .forEach(result::add);
            return result;
        }
    }

    private record RegistrationKey(UUID leagueId, UUID playerId) {
    }
}
