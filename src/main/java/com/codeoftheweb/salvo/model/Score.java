package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Score { // Score lists the score for each player in each game
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    //Atributos
    private long id; // Database-generated id
    private double score;
    private ZonedDateTime finishDate;

    public Score() { // Constructor vacío
    }

    public Score(double score, ZonedDateTime finishDate, Game game, Player player) {  // Constructor
        this.score = score;
        this.finishDate = finishDate;
        this.game = game;
        this.player = player;
    }

    @ManyToOne(fetch = FetchType.EAGER) // Many Scores to one game
    @JoinColumn(name="game_id") //
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER) // Many Scores to one player
    @JoinColumn(name="player_id") //
    private Player player;

     public double getScore() {  // Getters & Setters
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public ZonedDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(ZonedDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
