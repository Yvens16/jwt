package com.jwt_back17.jwt_back.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt_back17.jwt_back.dto.RegisterDto;
import com.jwt_back17.jwt_back.entity.User;
import com.jwt_back17.jwt_back.repository.UserRepository;
import com.jwt_back17.security.JwtGenerator;

@RestController
public class UserController {
  @Autowired
  private JwtGenerator tokenGenerator;


  //Ici méthode passwordEncoder rendu visible pour spring boot dans le fichier Config.java grâce à l'annotation @Bean
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;

  @GetMapping("try")
  public String getString() {
    return "Heloo";
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@RequestBody RegisterDto user) {
    // ResponseEntitty permet de renvoyer des messages avec un status http
    // Le point d'interrogation = any en typescript c'est le même principe

    /**
     * DTO : Data Transfer Object
     * Check if user already exists
     * Set encoded passwordss
     * set roles
     * save user
     */
    if (userRepository.existsByUsername(user.getUsername())) {
      // Ça peut être un check sur une autre propriété, l'email par example 
      return new ResponseEntity<>("Ce username existe déja, veuillez en choisir un autre", HttpStatus.BAD_REQUEST);
    }
    User newUser = new User();
    newUser.setUsername(user.getUsername());
    newUser.setEmail(user.getEmail());
    newUser.setRole("USER");

    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    // tokenGenerator est une classe custom crée pour encapsuler toutes les méthodes liées au JWT
    // On a du installer JJWT pour avoir accès à import io.jsonwebtoken
    String newToken = tokenGenerator.generateToken(user);
    // Hashmap c'est la même chose que les objets dans javascript, ce sont des paires de clé:valeur {clé: valeur}
    HashMap<String, String> map = new HashMap<String, String>(); 
    map.put("token", newToken);
    map.put("username", newUser.getUsername());
    map.put("password", newUser.getPassword());
    userRepository.save(newUser);

    // HttpStatus provient du package org.springframework.http permet d'avoir des enums des status désirés
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

}
