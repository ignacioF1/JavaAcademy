package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id; //Atributos
    private LocalDateTime created;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    @OrderBy
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="game", fetch= FetchType.EAGER)
    private Set<Score> scores;

    public Game() { //Constructor por defecto
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public Game(LocalDateTime created) { //Constructor sobrecargado
        this.created = created;
    }

    public long getId() { //Getters & Setters
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
