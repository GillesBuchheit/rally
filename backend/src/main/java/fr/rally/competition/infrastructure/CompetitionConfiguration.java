package fr.rally.competition.infrastructure;

import fr.rally.competition.application.CompetitionService;
import fr.rally.competition.application.LeagueRepository;
import fr.rally.competition.application.PlayerRepository;
import fr.rally.competition.application.RegistrationRepository;
import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CompetitionConfiguration {

    @Bean
    CompetitionService competitionService(LeagueRepository leagues, PlayerRepository players,
                                          RegistrationRepository registrations) {
        return new CompetitionService(leagues, players, registrations, Clock.systemUTC());
    }
}
