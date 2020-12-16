package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.*;
import com.codeoftheweb.salvo.repository.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    ShipRepository shipRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;
    @Autowired
    SalvoRepository salvoRepository;

    @RequestMapping(path = "/games/players/{gpId}/salvoes", method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> postSalvoes(@PathVariable long gpId, @RequestBody Salvo salvo, Authentication authentication) {
        if (Util.isGuest(authentication)) {   // Check if guest
            return new ResponseEntity<>(Util.makeMap("error", "Is guest"), HttpStatus.UNAUTHORIZED); //401
        }
        Player player = playerRepository.findByEmail(authentication.getName()); // Logged in player
        GamePlayer gamePlayer = gamePlayerRepository.findById(gpId).orElse(null);    // Brings the gamePlayer with the ID provided in the path variable
        if (gamePlayer == null) {   // Check if gamePlayer exists
            return new ResponseEntity<>(Util.makeMap("error", "No such gamePlayer."), HttpStatus.UNAUTHORIZED);  //401
        }
        Player thisGpPlayer = gamePlayer.getPlayer();    // Player for the provided gamePlayer
        if (player.getId() != thisGpPlayer.getId()) {  // Check if the logged in player is different from that gamePlayer's player
            return new ResponseEntity<>(Util.makeMap("error", "Not that gamePlayer player!"), HttpStatus.UNAUTHORIZED);    //401
        } else {
            if(Util.getOpponent(gamePlayer).getPlayer() == null){  // Check if the other player is present
                return new ResponseEntity<>(Util.makeMap("error", "The other player is not present!"), HttpStatus.FORBIDDEN);}  //403
            GamePlayer oponentGp = Util.getOpponent(gamePlayer);  // Oponent's gamePlayer
            int oponentTurn = oponentGp.getSalvoes().size();
            int thisPlTurn = gamePlayer.getSalvoes().size();
            if(thisPlTurn - oponentTurn > 0){   // If the other player has already played, do not allow
                return new ResponseEntity<>(Util.makeMap("error", "It is the other player turn!"), HttpStatus.FORBIDDEN);}  //403
            if(salvo.getLocations().size() > 5){
                return new ResponseEntity<>(Util.makeMap("error", "No more than 5 shots per salvo!"), HttpStatus.FORBIDDEN);}  //403

            int newTurn = thisPlTurn + 1;   // Agregar turno al salvo y luego guardarlo en el salvoRepo
            salvo.setTurn(newTurn);
            salvo.setGamePlayer(gamePlayer);
            salvoRepository.save(salvo);
            return new ResponseEntity<>(Util.makeMap("OK", "Salvo placed!"), HttpStatus.CREATED); //201
        }

    }
}


