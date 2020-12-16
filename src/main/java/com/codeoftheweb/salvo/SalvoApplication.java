package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.ZonedDateTime;
import java.util.Arrays;


@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	// Uncomment to add the test-bed
	/*@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository, GamePlayerRepository gamePlayerRepository, ShipRepository shipRepository, SalvoRepository salvoRepository, ScoreRepository scoreRepository) {
		return (args) -> {
			Player player1 = new Player("Jack Bauer", "j.bauer@ctu.gov", passwordEncoder().encode("123"));
			Player player2 = new Player("Chloe O'Brian", "c.obrian@ctu.gov", passwordEncoder().encode("123"));
			Player player3 = new Player("Kim Bauer", "kim_bauer@gmail.com", passwordEncoder().encode("123"));
			Player player4 = new Player("Tony Almeida", "t.almeida@ctu.gov", passwordEncoder().encode("123"));

			ZonedDateTime time = ZonedDateTime.now();
			ZonedDateTime time1 = time.plusHours(1);
			ZonedDateTime time2 = time.plusHours(2);
			ZonedDateTime time3 = time.plusHours(3);
			ZonedDateTime time4 = time.plusHours(4);
			ZonedDateTime time5 = time.plusHours(5);
			ZonedDateTime time6 = time.plusHours(6);
			ZonedDateTime time7 = time.plusHours(7);

			Game game1 = new Game(time); // Now
			Game game2 = new Game(time1); // 1 hour later
			Game game3 = new Game(time2); // 2 hours later
			Game game4 = new Game(time3); // 3 hours later
			Game game5 = new Game(time4); // 4 hours later
			Game game6 = new Game(time5); // 5 hours later
			Game game7 = new Game(time6); // 6 hours later
			Game game8 = new Game(time7); // 7 hours later

			GamePlayer gp1 = new GamePlayer(player1, game1);
			GamePlayer gp2 = new GamePlayer(player2, game1);
			GamePlayer gp3 = new GamePlayer(player1, game2);
			GamePlayer gp4 = new GamePlayer(player2, game2);
			GamePlayer gp5 = new GamePlayer(player2, game3);
			GamePlayer gp6 = new GamePlayer(player4, game3);
			GamePlayer gp7 = new GamePlayer(player2, game4);
			GamePlayer gp8 = new GamePlayer(player1, game4);
			GamePlayer gp9 = new GamePlayer(player4, game5);
			GamePlayer gp10 = new GamePlayer(player1, game5);
			GamePlayer gp11 = new GamePlayer(player3, game6);
			GamePlayer gp12 = new GamePlayer(player4, game7);
			GamePlayer gp13 = new GamePlayer(player3, game8);
			GamePlayer gp14 = new GamePlayer(player4, game8);

			playerRepository.save(player1); // Save players
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			gameRepository.save(game1); // Save games
			gameRepository.save(game2); //
			gameRepository.save(game3); //
			gameRepository.save(game4); //
			gameRepository.save(game5); //
			gameRepository.save(game6); //
			gameRepository.save(game7); //
			gameRepository.save(game8); //

			gamePlayerRepository.save(gp1); // Save gamePlayers
			gamePlayerRepository.save(gp2);
			gamePlayerRepository.save(gp3);
			gamePlayerRepository.save(gp4);
			gamePlayerRepository.save(gp5);
			gamePlayerRepository.save(gp6);
			gamePlayerRepository.save(gp7);
			gamePlayerRepository.save(gp8);
			gamePlayerRepository.save(gp9);
			gamePlayerRepository.save(gp10);
			gamePlayerRepository.save(gp11);
			gamePlayerRepository.save(gp12);
			gamePlayerRepository.save(gp13);
			gamePlayerRepository.save(gp14);

			Ship ship1 = new Ship("destroyer", Arrays.asList("H2", "H3", "H4"), gp1);
			Ship ship2 = new Ship("submarine", Arrays.asList("E1", "F1", "G1"), gp1);
			Ship ship3 = new Ship("patrolboat", Arrays.asList("B4", "B5"), gp1);
			Ship ship4 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp2);
			Ship ship5 = new Ship("patrolboat", Arrays.asList("F1", "F2"), gp2);
			Ship ship6 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp3);
			Ship ship7 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gp3);
			Ship ship8 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gp4);
			Ship ship9 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gp4);
			Ship ship10 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp5);
			Ship ship11 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gp5);
			Ship ship12 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gp6);
			Ship ship13 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gp6);
			Ship ship14 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp7);
			Ship ship15 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gp7);
			Ship ship16 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gp8);
			Ship ship17 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gp8);
			Ship ship18 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp9);
			Ship ship19 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gp9);
			Ship ship20 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gp10);
			Ship ship21 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gp10);
			Ship ship22 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp11);
			Ship ship23 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gp11);
			Ship ship24 = new Ship("destroyer", Arrays.asList("B5", "C5", "D5"), gp13);
			Ship ship25 = new Ship("patrolboat", Arrays.asList("C6", "C7"), gp13);
			Ship ship26 = new Ship("submarine", Arrays.asList("A2", "A3", "A4"), gp14);
			Ship ship27 = new Ship("patrolboat", Arrays.asList("G6", "H6"), gp14);

			shipRepository.save(ship1); // Save Ships
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);
			shipRepository.save(ship11);
			shipRepository.save(ship12);
			shipRepository.save(ship13);
			shipRepository.save(ship14);
			shipRepository.save(ship15);
			shipRepository.save(ship16);
			shipRepository.save(ship17);
			shipRepository.save(ship18);
			shipRepository.save(ship19);
			shipRepository.save(ship20);
			shipRepository.save(ship21);
			shipRepository.save(ship22);
			shipRepository.save(ship23);
			shipRepository.save(ship24);
			shipRepository.save(ship25);
			shipRepository.save(ship26);
			shipRepository.save(ship27);

			Salvo salvo1 = new Salvo(1, Arrays.asList("B5", "C5", "F1"), gp1); // Salvoes game 1
			Salvo salvo2 = new Salvo(1, Arrays.asList("B4", "B5", "B6"), gp2);
			Salvo salvo3 = new Salvo(2, Arrays.asList("F2", "D5"), gp1);
			Salvo salvo4 = new Salvo(2, Arrays.asList("E1", "H3", "A2"), gp2);

			Salvo salvo5 = new Salvo(1, Arrays.asList("A2", "A4", "G6"), gp3); // Salvoes game 2
			Salvo salvo6 = new Salvo(1, Arrays.asList("B5", "D5", "C7"), gp4);
			Salvo salvo7 = new Salvo(2, Arrays.asList("A3", "H6"), gp3);
			Salvo salvo8 = new Salvo(2, Arrays.asList("C5", "C6"), gp4);

			Salvo salvo9 = new Salvo(1, Arrays.asList("G6", "H6", "A4"), gp5); // Salvoes game 3
			Salvo salvo10 = new Salvo(1, Arrays.asList("H1", "H2", "H3"), gp6);
			Salvo salvo11 = new Salvo(2, Arrays.asList("A2", "A3", "D8"), gp5);
			Salvo salvo12 = new Salvo(2, Arrays.asList("E1", "F2", "G3"), gp6);

			Salvo salvo13 = new Salvo(1, Arrays.asList("A3", "A4", "F7"), gp7); // Salvoes game 4
			Salvo salvo14 = new Salvo(1, Arrays.asList("B5", "C6", "H1"), gp8);
			Salvo salvo15 = new Salvo(2, Arrays.asList("A2", "G6", "H6"), gp7);
			Salvo salvo16 = new Salvo(2, Arrays.asList("C5", "C7", "D5"), gp8);

			Salvo salvo17 = new Salvo(1, Arrays.asList("A1", "A2", "A3"), gp9); // Salvoes game 5
			Salvo salvo18 = new Salvo(1, Arrays.asList("B5", "B6", "C7"), gp10);
			Salvo salvo19 = new Salvo(2, Arrays.asList("G6", "G7", "G8"), gp9);
			Salvo salvo20 = new Salvo(2, Arrays.asList("C6", "D6", "E6"), gp10);
			Salvo salvo21 = new Salvo(3, Arrays.asList("H1", "H8"), gp9);

			salvoRepository.save(salvo1);    // Save Salvoes
			salvoRepository.save(salvo2);
			salvoRepository.save(salvo3);
			salvoRepository.save(salvo4);
			salvoRepository.save(salvo5);
			salvoRepository.save(salvo6);
			salvoRepository.save(salvo7);
			salvoRepository.save(salvo8);
			salvoRepository.save(salvo9);
			salvoRepository.save(salvo10);
			salvoRepository.save(salvo11);
			salvoRepository.save(salvo12);
			salvoRepository.save(salvo13);
			salvoRepository.save(salvo14);
			salvoRepository.save(salvo15);
			salvoRepository.save(salvo16);
			salvoRepository.save(salvo17);
			salvoRepository.save(salvo18);
			salvoRepository.save(salvo19);
			salvoRepository.save(salvo20);
			salvoRepository.save(salvo21);

			Score score1 = new Score(1, time.plusMinutes(30), game1, player1);
			Score score2 = new Score(0, time.plusMinutes(30), game1, player2);

			Score score3 = new Score(0.5, time1.plusMinutes(30), game2, player1);
			Score score4 = new Score(0.5, time1.plusMinutes(30), game2, player2);

			Score score5 = new Score(1, time2.plusMinutes(30), game3, player2);
			Score score6 = new Score(0, time2.plusMinutes(30), game3, player4);

			Score score7 = new Score(0.5, time3.plusMinutes(30), game4, player2);
			Score score8 = new Score(0.5, time3.plusMinutes(30), game4, player1);

			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
			scoreRepository.save(score5);
			scoreRepository.save(score6);
			scoreRepository.save(score7);
			scoreRepository.save(score8);

		};

	}*/
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByEmail(inputName); // username
			if (player != null) { // si existe se instancia la clase User de Spring
				return new User(player.getEmail(), player.getPassword(),
						AuthorityUtils.createAuthorityList("USER")); // Autentica al usuario como USER
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/**").permitAll() // no restringe static pero SI el servicio
				.antMatchers("/api/game_view/*").hasAuthority("USER") // el de tipo USER puede acceder
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/games").permitAll();

		// Enable X-Frame-Options for h2-console
		http.headers().frameOptions().sameOrigin();

		http.formLogin()
				.usernameParameter("name") // Ruta que recibe el .js
				.passwordParameter("pwd")
				.loginPage("/api/login"); // Spring maneja esta ruta

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}