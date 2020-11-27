package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.Game;
import com.codeoftheweb.salvo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository repo;

    public List<Game> getAll() {
    return repo.findAll();
    }

   // @RequestMapping("/games")
   //public List<Object> getGames() {
   //     return Game.getId();
  //  }
}
