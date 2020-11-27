package com.codeoftheweb.salvo.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id; //Atributos
    private String name;
    private String email;

    @OneToMany(mappedBy="player", fetch= FetchType.EAGER)
    Set<GamePlayer> gamePlayer;

    //public List<Game> getGames() {
    //   return gamePlayer.stream().map(sub -> sub.getGame()).collect(toList());
    //}

    public Player() { //Constructor por defecto
    }

    public Player(String name, String email) { //Constructor sobrecargado
        this.name = name;
        this.email = email;
    }

    public long getId() { //Getters y Setters (métodos get y set)
        return id;
    }

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
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name=" + name +
                ", email=" + email +
                '}';
    }
}