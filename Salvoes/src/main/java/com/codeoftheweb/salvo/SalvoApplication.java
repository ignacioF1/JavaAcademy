package com.codeoftheweb.salvo;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(PlayerRepository playerRepository, GameRepository gameRepository , GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			Player player1 = new Player("Jack", "jack@email.com");
			Player player2 = new Player("Matt", "matt@email.com");
			Player player3 = new Player("Brian", "brian@email.com");
			Player player4 = new Player("Lucas", "lucas@email.com");

			Game game1 = new Game(new Date()); // Now
			Game game2 = new Game(Date.from(new Date().toInstant().plusSeconds(3600))); // An hour later
			Game game3 = new Game(Date.from(new Date().toInstant().plusSeconds(7200))); // Two hours later

			GamePlayer gp1 = new GamePlayer(player1,game1);
			GamePlayer gp2 = new GamePlayer(player2,game2);
			GamePlayer gp3 = new GamePlayer(player3,game3);

			playerRepository.save(player1); // Save players
			playerRepository.save(player2);
			playerRepository.save(player3);
			playerRepository.save(player4);

			gameRepository.save(game1); // Save games
			gameRepository.save(game2); //
			gameRepository.save(game3); //

			gamePlayerRepository.save(gp1); // Save gamePlayers
			gamePlayerRepository.save(gp2);
			gamePlayerRepository.save(gp3);

		};

	}


}

