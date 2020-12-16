package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Salvo { // "Salvo is a set of shots"
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    //Atributos
    private long id; // Database-generated id
    private int turn;

    public Salvo() { // Constructor vacío
    }

    public Salvo(int turn, List<String> locations, GamePlayer gamePlayer) {    // Constructor
        this.turn = turn;
        this.locations = locations;
        this.gamePlayer = gamePlayer;
    }

    @ElementCollection // Create lists of embeddable objects
    @Column(name="location")
    private List<String> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER) // Many Salvoes to one gamePlayer
    @JoinColumn(name="gamePlayerId") //
    private GamePlayer gamePlayer;

    public long getId() {   // Getters & Setters
        return id;
    }

    public long getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}
