package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.*;
import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.repository.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private GamePlayerRepository gamePlayerRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    @RequestMapping("/players") //
    public List<Object> getPlayers() {
        PlayerDTO playerDTO = new PlayerDTO();
        return playerRepository.findAll().stream().map(player -> playerDTO.makePlayerDto(player)).collect(Collectors.toList());
    }

    @RequestMapping(path = "/game_view/{gamePlayerId}", method = RequestMethod.GET) // Returns ony one map per player
    public ResponseEntity<Map<String, Object>> getGameView(@PathVariable long gamePlayerId, Authentication authentication) {
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        Player player = playerRepository.findByEmail(authentication.getName());
        Player gpPlayer = gamePlayerRepository.findById(gamePlayerId).get().getPlayer();
        if (Util.isGuest(authentication)) {   // Check if guest
            return new ResponseEntity<>(Util.makeMap("error", "Not Logged in"), HttpStatus.UNAUTHORIZED);
        }
        if (player.getId() == gpPlayer.getId()) {    // Check if logged in player is that gamePlayer's player

            GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).orElse(null);
            if(Util.gameState(gamePlayer).equals("TIE")) {
                if (gamePlayer.getGame().getScores().size() < 2) {
                    Set<Score> scores = new HashSet<Score>();
                    Score score1 = new Score();
                    score1.setPlayer(gamePlayer.getPlayer());
                    score1.setGame(gamePlayer.getGame());
                    score1.setFinishDate(ZonedDateTime.now());
                    score1.setScore(0.5D);
                    scoreRepository.save(score1);
                    Score score2 = new Score();
                    score2.setPlayer(Util.getOpponent(gamePlayer).getPlayer());
                    score2.setGame(gamePlayer.getGame());
                    score2.setFinishDate(ZonedDateTime.now());
                    score2.setScore(0.5D);
                    scoreRepository.save(score2);
                    scores.add(score1);
                    scores.add(score2);

                    gamePlayer.getGame().setScores(scores); // Save scores to that game
                }
            }else if(Util.gameState(gamePlayer).equals("WON")) {
                if (gamePlayer.getGame().getScores().size() < 2) {
                    Set<Score> scores = new HashSet<Score>();
                    Score score1 = new Score();
                    score1.setPlayer(gamePlayer.getPlayer());
                    score1.setGame(gamePlayer.getGame());
                    score1.setFinishDate(ZonedDateTime.now());
                    score1.setScore(1.0D);
                    scoreRepository.save(score1);
                    Score score2 = new Score();
                    score2.setPlayer(Util.getOpponent(gamePlayer).getPlayer());
                    score2.setGame(gamePlayer.getGame());
                    score2.setFinishDate(ZonedDateTime.now());
                    score2.setScore(0.0D);
                    scoreRepository.save(score2);
                    scores.add(score1);
                    scores.add(score2);

                    Util.getOpponent(gamePlayer).getGame().setScores(scores); // Save scores to that game
                }
            }

            return new ResponseEntity<>(gamePlayerDTO.makeGameView(gamePlayerRepository.findById(gamePlayerId).orElse(null)), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(Util.makeMap("error", "Not the Logged in player"), HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping("/leaderBoard") //
    public List<Object> getScores() {
        PlayerDTO playerDTO = new PlayerDTO();

        return playerRepository.findAll().stream().map(player -> playerDTO.makeScoreDto(player)).collect(Collectors.toList());
    }
}



