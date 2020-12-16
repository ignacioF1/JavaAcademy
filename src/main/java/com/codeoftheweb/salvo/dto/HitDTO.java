package com.codeoftheweb.salvo.dto;

import com.codeoftheweb.salvo.model.*;
import com.codeoftheweb.salvo.repository.util.Util;
import java.util.*;
import java.util.stream.Collectors;

public class HitDTO {

    private Game game;

    public HitDTO() { // Default constructor
    }

    public Map<String, Object> makeHitDto(GamePlayer gpPlayer1, Salvo salvo1) {
        Map<String, Object> dto = new LinkedHashMap<>();
        GamePlayer gpPlayer2 = Util.getOpponent(gpPlayer1); //Get the other player gamePlayer
        Set p2Ships = gpPlayer2.getShips(); //Ships from player2
        Set<Ship> opponentShips = gpPlayer2.getShips();
        List<String> p1Salvoes = new ArrayList<>();
        gpPlayer1.getSalvoes().forEach(salvo -> p1Salvoes.addAll(salvo.getLocations())); //p1Salvoes stores the locations of every salvo from player1
        Set<Salvo> mySalvoes = new HashSet<Salvo>(gpPlayer1.getSalvoes()
                .stream().filter(salvo -> salvo.getTurn() <= salvo1.getTurn()).collect(Collectors.toList())); // mySalvoes stores player1 salvoes from the first turn up to the turn of the salvo received as argument
        dto.put("turn", salvo1.getTurn());
        dto.put("hitLocations", getHitLocs(salvo1.getLocations(), p2Ships));
        dto.put("damages", getDamages(mySalvoes, opponentShips));
        dto.put("missed", salvo1.getLocations().size() - getHitLocs(salvo1.getLocations(), p2Ships).size());

        return dto;
    }

    public long getHitShips(GamePlayer gpPlayer1) { //Get the total number of hits up to this game state
        if (gpPlayer1.getPlayer() == null){ // if player is null, return 0
            return 0;
        }
        GamePlayer gpPlayer2 = Util.getOpponent(gpPlayer1); //Get the other player gamePlayer
        Set<Ship> opponentShips = gpPlayer2.getShips();
        Set<Salvo> allSalvoes = new HashSet<Salvo>(gpPlayer1.getSalvoes());
        Map<String,Long> hitLocs =  getDamages(allSalvoes, opponentShips);
        Long carrier = hitLocs.get("carrier");
        Long battleship = hitLocs.get("battleship");
        Long submarine = hitLocs.get("submarine");
        Long destroyer = hitLocs.get("destroyer");
        Long patrolboat = hitLocs.get("patrolboat");
        return (carrier + battleship + submarine + destroyer + patrolboat);
        }

    public List<String> getHitLocs (List<String> mySalvoes, Set<Ship> opponentShips) {  // Returns a list with the locations of the salvoes that hitted a ship
        List<String> allOpShips = new ArrayList<>();    // allOpShips stores all opponent ship locations in one single list
            opponentShips.forEach(ship -> allOpShips.addAll(ship.getLocations()));
            return mySalvoes
                    .stream().filter(salvo -> allOpShips
                            .stream().anyMatch(loc -> loc.equals(salvo))).collect(Collectors.toList());
    }

    public Map<String,Long> getDamages (Set<Salvo> mySalvoes, Set<Ship> opponentShips) {    // Returns a list with the hitted ships
        Map<String,Long> damage = new LinkedHashMap<>();
        List<String> allMySalvoes = new ArrayList<>(); // allMySalvoes stores all salvo locations in one single list
        List<String> salvoThisTurn = new ArrayList<>();
        mySalvoes.forEach(salvo -> allMySalvoes.addAll(salvo.getLocations()));

        List<Ship> damShips = new ArrayList<>(); // damShips stores the ships hitted from the beginning up to this turn
        List<Ship> damShipsTurn = new ArrayList<>(); // damShipsTurn stores the ships hitted in this turn only

        if (mySalvoes.size() != 0) {
            long thisTurn = mySalvoes.stream().max(Comparator.comparing(Salvo::getTurn)).get().getTurn(); // thisTurn stores the last turn number played
            salvoThisTurn = mySalvoes.stream().filter(salvo -> salvo.getTurn() == thisTurn).findFirst().get().getLocations(); // salvoThisTurn stores the locations of the salvo from the last turn
            // damShips stores the ships hitted from the beginning up to this turn
            for (String loc : allMySalvoes) {
                damShips.addAll(opponentShips.stream().filter(ship -> ship.getLocations().contains(loc))
                        .collect(Collectors.toList()));
            }

            // damShipsTurn stores the ships hitted in this turn only
            for (String loc : salvoThisTurn) {
                damShipsTurn.addAll(opponentShips.stream().filter(ship -> ship.getLocations().contains(loc))
                        .collect(Collectors.toList()));
            }
        }
         // Calculate number of hits per ship type per turn
        for (String currentType : List.of("carrier", "battleship", "submarine", "destroyer", "patrolboat")) {
            damage.put(currentType + "Hits", damShipsTurn.stream().filter(ship -> ship.getType().equals(currentType)).count());}

        // Calculate total number of hits per ship type
        for (String currentType : List.of("carrier", "battleship", "submarine", "destroyer", "patrolboat")) {
         damage.put(currentType, damShips.stream().filter(ship -> ship.getType().equals(currentType)).count());}
                 return damage;
    }

}
