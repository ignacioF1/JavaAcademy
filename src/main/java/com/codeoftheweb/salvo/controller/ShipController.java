package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Ship;
import com.codeoftheweb.salvo.repository.GamePlayerRepository;
import com.codeoftheweb.salvo.repository.GameRepository;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import com.codeoftheweb.salvo.repository.ShipRepository;
import com.codeoftheweb.salvo.repository.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ShipController {

    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @RequestMapping(path = "/games/players/{gpId}/ships", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> postShips(@PathVariable long gpId, @RequestBody List <Ship> shipList, Authentication authentication) {
        if (Util.isGuest(authentication)) {   // Check if guest
            return new ResponseEntity<>(Util.makeMap("error", "Is guest"), HttpStatus.UNAUTHORIZED); //401
        }
        Player player = playerRepository.findByEmail(authentication.getName()); // Logged in player
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId).orElse(null);    // Brings the gamePlayer gpId provided in the path variable
        if (gamePlayer == null) {   // Check if gamePlayer exists
            return new ResponseEntity<>(Util.makeMap("error", "No such gamePlayer."), HttpStatus.FORBIDDEN);  //403
        }
        Player playerGp = gamePlayer.getPlayer();    // Player for the requested gamePlayer
        if (player.getId() != playerGp.getId()) {  // Check if the logged in player is different from that gamePlayer's player
            return new ResponseEntity<>(Util.makeMap("error", "Not that gamePlayer player!"), HttpStatus.UNAUTHORIZED);    //401
        } else {
            if (gamePlayer.getShips().size() == 5) {
                return new ResponseEntity<>(Util.makeMap("error", "Ships already placed!"), HttpStatus.FORBIDDEN);  //403
            }
            if (gamePlayer.getShips().size() + shipList.size() > 5) {   //Check if actual ships + new ones >5
                return new ResponseEntity<>(Util.makeMap("error", "Too much ships to place!"), HttpStatus.FORBIDDEN);  //403
            }
            for (String currentType : List.of("carrier", "battleship", "submarine", "destroyer", "patrolboat")) {// Check if the type of ship was already placed
                if ((shipList.stream().filter(ship -> ship.getType().equals(currentType)).count())>1){
                    return new ResponseEntity<>(Util.makeMap("error", "Too many " + currentType + "s" + "!"), HttpStatus.FORBIDDEN);  //403
                }
            }
            // Agregar gamePlayer a cada ship recibido y luego guardarlo en el shipRepo
            List shipListOut = shipList.stream().map(ship -> ship.setGamePlayer(gamePlayer)).collect(Collectors.toList());
            shipRepository.saveAll(shipListOut);
            return new ResponseEntity<>(Util.makeMap("OK", "Ships placed!"), HttpStatus.CREATED); //201
        }

    }
}


