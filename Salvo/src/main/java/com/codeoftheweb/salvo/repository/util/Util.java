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

public static Optional<GamePlayer> getOpponent(GamePlayer gamePlayer) {
        GamePlayer opponent = gamePlayer.getGame().getGamePlayers().stream()
                .filter(gp -> gp.getId() != gamePlayer.getId()).findFirst().get();
        return Optional.of(opponent);
    }
}
