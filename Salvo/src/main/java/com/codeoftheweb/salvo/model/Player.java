package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id; //Atributos
    private String name;
    private String email;
    private String password;

    private double total;
    private int won;
    private int lost;
    private int tied;

    @OneToMany(mappedBy="player", fetch= FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;

    @OneToMany(mappedBy="player", fetch= FetchType.EAGER)
    @OrderBy
    private Set<Score> scores;

    public Player() { //Constructor por defecto
    }

    public Player(String name, String email, String password) { //Constructor sobrecargado
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Player(String email, String password) { //Constructor sobrecargado
        this.email = email;
        this.password = password;
    }

    public double getTotalScore() {
        return getWinScore() * 1.0 + getTiedScore() * 0.5D;
    }
    public long getWinScore() {
        return this.getScores().stream().filter(score -> score.getScore() == 1.0D).count();
    }
    public long getLostScore() {
        return this.getScores().stream().filter(score -> score.getScore() == 0.0D).count();
    }
    public long getTiedScore(){
        return this.getScores().stream().filter(score -> score.getScore() == 0.5D).count();
    }

    public Score getScoreByGame (Game game){
        return this.scores.stream().filter(score -> score.getGame().getId() == game.getId()).findFirst().orElse(null);
    }

    public String getPassword() { // Getters & Setters
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public long getId() { //Getters y Setters (métodos get y set)
        return id;
    } // Getters & Setters

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() { // toString
        return "Player{" +
                "id=" + id +
                ", name=" + name +
                ", email=" + email +
                '}';
    }
}