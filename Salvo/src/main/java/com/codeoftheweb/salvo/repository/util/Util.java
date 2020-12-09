package com.codeoftheweb.salvo.repository.util;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

public class Util {
public static Map<String ,Object> makeMap(String key, Object value){
    Map<String , Object> map = new HashMap<>();
    map.put(key, value);
    return map;
}
public static boolean isGuest(Authentication authentication){ // Check if someone is logged in
    return authentication == null || authentication instanceof AnonymousAuthenticationToken;
}
}