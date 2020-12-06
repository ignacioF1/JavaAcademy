package com.codeoftheweb.salvo.controller;

import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api")
    public class PlayerController{

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PlayerRepository playerRepository;
        @RequestMapping(path = "/players", method = RequestMethod.POST)
        public ResponseEntity<Object> register(@RequestParam String email, @RequestParam String password) {

            if (email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if (playerRepository.findByEmail(email) !=  null) {
                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
            }

            playerRepository.save(new Player(null, email, passwordEncoder.encode(password)));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
}

// En consola:
// Log in
//$.post("/api/login", { name: "j.bauer@ctu.gov", pwd: "123" }).done(function() { console.log("logged in!"); })
// Log out
//$.post("/api/logout").done(function() { console.log("logged out"); })
// Crear user
//$.post("/api/players",{email:"david@gmail.com",password: "123"})