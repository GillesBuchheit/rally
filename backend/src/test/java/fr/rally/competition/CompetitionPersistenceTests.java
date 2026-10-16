package fr.rally.competition;

import fr.rally.competition.application.CompetitionException;
import fr.rally.competition.application.CompetitionService;
import fr.rally.competition.domain.League;
import fr.rally.competition.domain.Player;
import fr.rally.competition.domain.Registration;
import fr.rally.competition.domain.Sport;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class CompetitionPersistenceTests {

    @Autowired
    private CompetitionService competition;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void aPlayerCanJoinSeveralLeaguesButOnlyOncePerLeague() {
        UUID ownerId = UUID.randomUUID();
        Player player = competition.createPlayer("  Camille Martin  ");
        League tennis = competition.createLeague("  Ligue de Paris  ", Sport.TENNIS, ownerId);
        League padel = competition.createLeague("Ligue de Lyon", Sport.PADEL, ownerId);

        Registration first = competition.registerPlayer(tennis.id(), player.id(), ownerId);
        Registration second = competition.registerPlayer(padel.id(), player.id(), ownerId);

        assertThat(first.playerId()).isEqualTo(second.playerId());
        assertThat(competition.listPlayers(tennis.id())).containsExactly(player);
        assertThat(competition.listPlayers(padel.id())).containsExactly(player);
        assertThat(tennis.name()).isEqualTo("Ligue de Paris");
        assertThat(player.displayName()).isEqualTo("Camille Martin");
        assertThatThrownBy(() -> competition.registerPlayer(tennis.id(), player.id(), ownerId))
                .isInstanceOf(CompetitionException.class)
                .satisfies(error -> assertThat(((CompetitionException) error).code())
                        .isEqualTo(CompetitionException.Code.PLAYER_ALREADY_REGISTERED));
    }

    @Test
    void onlyTheOwnerCanChangeRegistrations() {
        UUID ownerId = UUID.randomUUID();
        League league = competition.createLeague("Open", Sport.TENNIS, ownerId);
        Player player = competition.createPlayer("Nora");

        assertThatThrownBy(() -> competition.registerPlayer(league.id(), player.id(), UUID.randomUUID()))
                .isInstanceOf(CompetitionException.class)
                .satisfies(error -> assertThat(((CompetitionException) error).code())
                        .isEqualTo(CompetitionException.Code.NOT_LEAGUE_OWNER));

        competition.registerPlayer(league.id(), player.id(), ownerId);
        competition.removePlayer(league.id(), player.id(), ownerId);
        assertThat(competition.listPlayers(league.id())).isEmpty();
    }

    @Test
    void databaseRejectsDuplicateRegistrations() {
        UUID ownerId = UUID.randomUUID();
        League league = competition.createLeague("Open", Sport.PADEL, ownerId);
        Player player = competition.createPlayer("Alex");
        competition.registerPlayer(league.id(), player.id(), ownerId);

        assertThatThrownBy(() -> jdbc.update(
                "insert into rally.league_registrations (league_id, player_id, joined_at) values (?, ?, current_timestamp)",
                league.id(), player.id()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
