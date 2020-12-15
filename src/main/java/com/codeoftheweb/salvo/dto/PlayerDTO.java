package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;

public class PlayerDTO {

    private Player player;

    public PlayerDTO() { // Default constructor
    }

    public PlayerDTO(Player player) { // Constructor

        this.player = player;
    }

    public Map<String, Object> makePlayerDto(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("name", player.getName());
        dto.put("email", player.getEmail());
        return dto;

    }

    public Map<String, Object> makeScoreDto(Player player) {
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> score = new LinkedHashMap<>();
        dto.put("id", player.getId());
        dto.put("email", player.getEmail());
        dto.put("score", score);
        score.put("total", player.getTotalScore());
        score.put("won", player.getWinScore());
        score.put("lost", player.getLostScore());
        score.put("tied", player.getTiedScore());
        return  dto;
    }

    public Player getPlayer() { // Getters & Setters
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}