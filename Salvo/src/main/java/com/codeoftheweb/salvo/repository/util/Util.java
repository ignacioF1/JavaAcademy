package com.codeoftheweb.salvo.repository.util;


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

    public static String gameState(GamePlayer gamePlayer){
        if(gamePlayer.getShips().isEmpty()){
            return "PLACESHIPS";
        }
        if(gamePlayer.getGame().getGamePlayers().size() == 1){
            return "WAITINGFOROPP";
        }

        return "PLAY";
    }
}
