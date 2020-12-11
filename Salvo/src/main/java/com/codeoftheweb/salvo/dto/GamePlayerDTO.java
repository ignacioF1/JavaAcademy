package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Score;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GamePlayerDTO {

    private GamePlayer gamePlayer;

    public GamePlayerDTO() { // Default constructor
    }

    public GamePlayerDTO(GamePlayer gamePlayer) { // Constructor
        this.gamePlayer = gamePlayer;
    }

    public Map<String, Object> makeGamePlayerDto(GamePlayer gamePlayer) {
        PlayerDTO playerDTO = new PlayerDTO();
        Map<String, Object> dto = new LinkedHashMap<>();
        dto.put("id", gamePlayer.getId());
        dto.put("player", playerDTO.makePlayerDto(gamePlayer.getPlayer()));
        return dto;
    }

    public GamePlayer getGamePlayer() { // Getters & Setters
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public Map<String, Object> makeGameView(GamePlayer gamePlayer) { // GameView represents a player in the game
        Map<String, Object> dto = new LinkedHashMap<>();
        Map<String, Object> hits= new LinkedHashMap<>();
        if (gamePlayer != null) {
            ShipDTO shipDTO = new ShipDTO();
            SalvoDTO salvoDTO = new SalvoDTO();
            GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
            hits.put("self", new ArrayList<>());
            hits.put("opponent", new ArrayList<>());
            dto.put("id", gamePlayer.getGame().getId());
            dto.put("created", gamePlayer.getGame().getCreated());
            dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(gamePlayer1 -> gamePlayerDTO.makeGamePlayerDto(gamePlayer1)));
            dto.put("ships", gamePlayer.getShips().stream().map(ship -> shipDTO.makeShipDto(ship)));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                    .stream().flatMap(gp -> gp.getSalvoes()
                        .stream().map(salvo -> salvoDTO.makeSalvoDto(salvo))));
            // Obtengo el juego, luego los GamePlayers, y luego los salvoes de ambos jugadores, a los que paso por el dto.
            // flatMap devuelve una sola colección con los resultados de ambas iteraciones
            dto.put("hits",hits);
            //dto.put("gameState","PLACESHIPS"); test placing ships
            dto.put("gameState","PLAY");    // test placing salvoes
        } else {
            dto.put("Error", "no such game");
        }
        return dto;
    }
}