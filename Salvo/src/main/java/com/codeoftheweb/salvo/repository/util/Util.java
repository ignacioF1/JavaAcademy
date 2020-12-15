package com.codeoftheweb.salvo.repository.util;


import com.codeoftheweb.salvo.dto.HitDTO;
import com.codeoftheweb.salvo.model.GamePlayer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Util {
public static Map<String ,Object> makeMap(String key, Object value){
    Map<String , Object> map = new HashMap<>();
    map.put(key, value);
    return map;
}
public static boolean isGuest(Authentication authentication){ // Check if someone is logged in
    return authentication == null || authentication instanceof AnonymousAuthenticationToken;
}

public static GamePlayer getOpponent(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getGamePlayers().stream()
                .filter(gp -> gp.getId() != gamePlayer.getId()).findAny().orElse(new GamePlayer());
        return opponent;
    }

     
         
    public static String gameState(GamePlayer gamePlayer){ // Get the game state
    Integer numOfPlayers = gamePlayer.getGame().getGamePlayers().size();
    HitDTO hitDTO = new HitDTO();
    long oppHitsCount = hitDTO.getHitShips(gamePlayer); // Get the total number of hits in the opponent ships
    long myHitsCount = hitDTO.getHitShips(getOpponent(gamePlayer)); // Get the total number of hits in my ships

        if (gamePlayer.getShips().isEmpty()) { // If no ships present, go to the "PLACESHIPS" state
            return "PLACESHIPS";
        }else if( (numOfPlayers == 1) || getOpponent(gamePlayer).getShips().size() == 0 ){ // If one player still have to place ships, wait for the other
            return "WAITINGFOROPP";
        }else if(gamePlayer.getSalvoes().size() > getOpponent(gamePlayer).getSalvoes().size()) { // If opponent has less salvoes than the player, wait state
            return "WAIT";
        }else if(myHitsCount == 17 && oppHitsCount == 17){ // 17 is the number of hits required to sink all of the ships
            return  "TIE";
        }else if(myHitsCount == 17){
            return "LOSE";
        }else if(oppHitsCount == 17){
            return "WON";
        }else{
            return "PLAY";
        }
    }
}
