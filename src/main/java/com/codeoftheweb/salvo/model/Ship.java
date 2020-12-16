package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    //Atributos
    private long id; // Database-generated id
    private String type;

    public Ship() { // Constructor vacío
    }

    public Ship(String type, List<String> locations, GamePlayer gamePlayer) { // Constructor
        this.type = type;
        this.locations = locations;
        this.gamePlayer = gamePlayer;
    }

    @ElementCollection // Create lists of embeddable objects
    @Column(name="location")
    private List<String> locations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER) // Many ships to one gamePlayer
    @JoinColumn(name="gamePlayerId") //
    private GamePlayer gamePlayer;

    public long getId() { // Getters & Setters
        return id;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Ship setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        return this;
    }
}
