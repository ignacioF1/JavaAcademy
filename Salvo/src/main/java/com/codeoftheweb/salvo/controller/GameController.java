package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.dto.GameDTO;
import com.codeoftheweb.salvo.dto.PlayerDTO;
import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeoftheweb.salvo.repository.util.Util.makeMap;

@RestController
@RequestMapping("/api")
    public class GameController {


        @Autowired
        PlayerRepository playerRepository;
        @Autowired
        GameRepository gameRepository;
        @Autowired
        GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/games")
    public Map<String, Object> getGameAll(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();

        if (Util.isGuest(authentication)) {
            dto.put("player", "Guest");
        } else {
            Player player = playerRepository.findByEmail(authentication.getName());
            PlayerDTO playerDTO = new PlayerDTO(player);
            dto.put("player", playerDTO.makePlayerDto(player));
        }

        dto.put("games", gameRepository.findAll()
                .stream()
                .map(game -> {
                    GameDTO gameDTO = new GameDTO(game);
                    return gameDTO.makeGameDto(game);
                })
                .collect(Collectors.toList()));

        return dto;

    }

        @RequestMapping(path = "/games", method = RequestMethod.POST)   // Create a game
        public ResponseEntity<Object> createGame(Authentication authentication) {
            if (Util.isGuest(authentication)) {   // Check if guest
                return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
            }

            Player player = playerRepository.findByEmail(authentication.getName());

            if (player == null) {   // Check if user exists
                return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
            }

        Game game = gameRepository.save(new Game(LocalDateTime.now()));   // Create new game and new gamePlayer
        GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player , game));
    return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()),HttpStatus.CREATED); // Returns gpid,nn
    }

    @RequestMapping("/game/{idGame}/players")   // Join a game
    public ResponseEntity<Map<String ,Object>> JoinGame(@PathVariable long idGame, Authentication authentication){
        if(Util.isGuest(authentication)){   // Check if guest
            return new ResponseEntity<>(Util.makeMap("error","Is guest"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName()); // Logged in player
        Game gameToJoin = gameRepository.findById(idGame).orElse(null);    // Brings the game with idGame provided in the path variable
        if(gameToJoin == null){ // ver si el juego existe
            return new ResponseEntity<>(Util.makeMap("error" , "No such game."),HttpStatus.FORBIDDEN);
        }
        long gamePlayersCount = gameToJoin.getGamePlayers().size();
        if(gamePlayersCount != 1){  // Check if game has only one player
        return new ResponseEntity<>(Util.makeMap("error" , "Game is full!"),HttpStatus.FORBIDDEN);}
        Player firstPlayer = gameToJoin.getGamePlayers().stream().map(gp -> gp.getPlayer()).findFirst().get();  // Get the first player
        if(player.getId() != firstPlayer.getId()){  // Check if the player is different from the first one
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, gameToJoin));  // Create a new gamePlayer
            return new ResponseEntity<>(Util.makeMap("gpid" , gamePlayer.getId()),HttpStatus.CREATED); // Returns gpid,nn
        }else{
            return new ResponseEntity<>(Util.makeMap("error" , "Already member!"),HttpStatus.FORBIDDEN);    // First and second players cannot be the same
        }

    }

}
