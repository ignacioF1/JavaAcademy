package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Score;
import com.codeoftheweb.salvo.repository.util.Util;
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
            HitDTO hitDTO = new HitDTO();
            GamePlayer gpPlayer2 = Util.getOpponent(gamePlayer); // Gets player2
                    hits.put("self", gamePlayer.getSalvoes()
                            .stream().map(salvo -> hitDTO.makeHitDto(gamePlayer,salvo))); // Calls maheHitDto() for every salvo in player1's gamePlayer
                    hits.put("opponent", gpPlayer2.getSalvoes()
                            .stream().map(salvo -> hitDTO.makeHitDto(gpPlayer2,salvo))); // Calls maheHitDto() for every salvo in player2's gamePlayer
            ShipDTO shipDTO = new ShipDTO();
            SalvoDTO salvoDTO = new SalvoDTO();
            GamePlayerDTO gamePlayerDTO = new GamePlayerDTO();
            dto.put("id", gamePlayer.getGame().getId());
            dto.put("created", gamePlayer.getGame().getCreated());
            dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(gamePlayer1 -> gamePlayerDTO.makeGamePlayerDto(gamePlayer1)));
            dto.put("ships", gamePlayer.getShips().stream().map(ship -> shipDTO.makeShipDto(ship)));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers()
                    .stream().flatMap(gp -> gp.getSalvoes()
                        .stream().map(salvo -> salvoDTO.makeSalvoDto(salvo))));
            // Obtain the game, then the gamePlayers, and then each player's salvoes, to which the dto is applied.
            // flatMap returns a single collection with the results of both iterations
            dto.put("hits",hits);
            //dto.put("gameState","PLACESHIPS"); test placing ships
            //dto.put("gameState","PLAY");    // test firing salvoes
            dto.put("gameState","PLAY");


        } else {
            dto.put("Error", "no such game");
        }
        return dto;
    }
}