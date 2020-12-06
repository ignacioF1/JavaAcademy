package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.model.Score;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameDTO {

    private Game game;

    public GameDTO() { // Default constructor
    }

    public GameDTO(Game game) { // Constructor
        this.game = game;
    } // Constructor

    public Map<String,Object> makeGameDto(Game game){
        GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
        ScoreDTO scoreDTO = new ScoreDTO();
        Map<String,Object> dto = new LinkedHashMap<>();
        dto.put("id",game.getId());
        dto.put("created",game.getCreated());
        dto.put("gamePlayers",game.getGamePlayers().stream().map(gamePlayer -> gamePlayerDTO.makeGamePlayerDto(gamePlayer)).collect(Collectors.toList()));;
        dto.put("scores", game.getScores().stream().map(score -> scoreDTO.makeScoreDTO(score)).collect(Collectors.toList()));

        return dto;
    }

    public Game getGame() { // Setters & Getters
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}

