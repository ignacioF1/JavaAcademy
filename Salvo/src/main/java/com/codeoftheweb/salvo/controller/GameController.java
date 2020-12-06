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

        @RequestMapping(path = "/games", method = RequestMethod.POST)
        public ResponseEntity<Object> createGame(Authentication authentication) {
            if (Util.isGuest(authentication)) {
                return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
            }

            Player player = playerRepository.findByEmail(authentication.getName());

            if (player == null) {
                return new ResponseEntity<>("NO esta autorizado", HttpStatus.UNAUTHORIZED);
            }

        Game game = gameRepository.save(new Game());
        GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player , game));
    return new ResponseEntity<>(makeMap("gpid",gamePlayer.getId()),HttpStatus.CREATED);
    }

    @RequestMapping("/game/{idGame}/players")
    public ResponseEntity<Map<String ,Object>> JoinGame(@PathVariable long idGame, Authentication authentication){
        if(Util.isGuest(authentication)){
            return new ResponseEntity<>(Util.makeMap("error","Is guest"), HttpStatus.UNAUTHORIZED);
        }
        Player player = playerRepository.findByEmail(authentication.getName());
        Game gameToJoin = gameRepository.getOne(idGame);

        if(gameToJoin == null){ // ver si el juego existe
            return new ResponseEntity<>(Util.makeMap("error" , "No such game."),HttpStatus.CREATED);
        }
        long gamePlayersCount = gameToJoin.getGamePlayers().size();

        if(gamePlayersCount == 1){
            GamePlayer gamePlayer = gamePlayerRepository.save(new GamePlayer(player, gameToJoin));
            return new ResponseEntity<>(Util.makeMap("gpid" , gamePlayer.getId()),HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Util.makeMap("error" , "Game is full!"),HttpStatus.FORBIDDEN);
        }

    }

}
