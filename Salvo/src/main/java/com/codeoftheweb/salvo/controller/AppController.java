package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.*;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.repository.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    if(Util.isGuest(authentication)){
        return new ResponseEntity<>(Util.makeMap("error" , "Not Logged in"),HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(gamePlayerDTO.makeGameView(gamePlayerRepository.findById(gamePlayerId).orElse(null)),HttpStatus.ACCEPTED);
    }

    @RequestMapping("/leaderBoard") //
    public List<Object> getScores() {
        PlayerDTO playerDTO = new PlayerDTO();
        return playerRepository.findAll().stream().map(player -> playerDTO.makeScoreDto(player)).collect(Collectors.toList());
    }
}



