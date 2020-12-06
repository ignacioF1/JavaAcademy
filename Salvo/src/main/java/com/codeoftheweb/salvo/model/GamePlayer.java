package com.codeoftheweb.salvo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

@Entity
public class GamePlayer { // Represents "a player in a particular game."
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private Date joinDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy = "gamePlayer",fetch = FetchType.EAGER)
    @OrderBy // Order ships by id
    private Set<Ship> ships;

    @OneToMany(mappedBy = "gamePlayer",fetch = FetchType.EAGER)
    @OrderBy // Order salvoes by id
    private Set<Salvo> salvoes;

    public GamePlayer() { // Default constructor
    }

    public GamePlayer(Player player, Game game) { // Constructor
        this.game = game;
        this.player = player;
        this.joinDate = new Date();
    }

    public long getId() {
        return id;
    } // Getters & Setters

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }
}