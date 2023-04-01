package com.jwt_back17.jwt_back.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jwt_back17.jwt_back.dto.LoginDto;
import com.jwt_back17.jwt_back.dto.RegisterDto;
import com.jwt_back17.jwt_back.entity.BlackListTokenEntity;
import com.jwt_back17.jwt_back.entity.UserEntity;
import com.jwt_back17.jwt_back.repository.BlackListTokenRepository;
import com.jwt_back17.jwt_back.repository.UserRepository;
import com.jwt_back17.security.JwtFilter;
import com.jwt_back17.security.JwtGenerator;

@RestController
public class UserController {
  @Autowired
  private JwtGenerator tokenGenerator;

  @Autowired
  JwtFilter jwtFilter;

  @Autowired
  BlackListTokenRepository blacklistRepository;

  // Ici méthode passwordEncoder rendu visible pour spring boot dans le fichier
  // Config.java grâce à l'annotation @Bean
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
    UserEntity newUser = new UserEntity();
    newUser.setUsername(user.getUsername());
    newUser.setEmail(user.getEmail());
    newUser.setRole("USER");

    newUser.setPassword(passwordEncoder.encode(user.getPassword()));
    // tokenGenerator est une classe custom crée pour encapsuler toutes les méthodes
    // liées au JWT
    // On a du installer JJWT pour avoir accès à import io.jsonwebtoken
    String newToken = tokenGenerator.generateToken(user);
    // Hashmap c'est la même chose que les objets dans javascript, ce sont des
    // paires de clé:valeur {clé: valeur}
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("token", newToken);
    userRepository.save(newUser);

    // HttpStatus provient du package org.springframework.http permet d'avoir des
    // enums des status désirés
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @PostMapping("login")
  public ResponseEntity<?> login(@RequestBody RegisterDto userAuth) {
    HashMap<String, String> map = new HashMap<String, String>();
    Optional<UserEntity> optionnalUser = userRepository.findByUsername(userAuth.getUsername());
    if (optionnalUser.isPresent()) {
      UserEntity user = optionnalUser.get();
      String normalPassword = userAuth.getPassword();
      String hashedPassword = user.getPassword();

      if (passwordEncoder.matches(normalPassword, hashedPassword)) {
        String newToken = tokenGenerator.generateToken(userAuth);
        map.put("token", newToken);
        map.put("message", "connexion réussie");
        return new ResponseEntity<>(map, HttpStatus.OK);
      }
      map.put("message", "Le mot de passe n'est pas bon");
      return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
    map.put("message", "Cet utilisateur n'existe pas");
    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
  }

  @PostMapping("customlogout")
  public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
    // Obtenir le token depuis la requête http
    System.out.println("@@@@@@@@ " + bearerToken);
    String token = jwtFilter.getTokenString(bearerToken);
    BlackListTokenEntity tokenToBlackList = new BlackListTokenEntity();
    tokenToBlackList.setToken(token);
    blacklistRepository.save(tokenToBlackList);
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("message", "logout réussie");
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping("logoutSuccessfully")
  public ResponseEntity<String> logoutSuccessfully() {
    // Par défault spring security cherche un url où renvoyer l'utilisateur en cas de logout
    // C'est pour ça que l'on configure cette url pour l'instant dans le fichier Config.java 
    // Allez voir dans le fichier Config.java à cette ligne: "http.logout().logoutSuccessUrl("/logoutSuccessfully");"
    // On le remplacera sûrement par la dernière page visité ou une page prévu à cette effet
    return new ResponseEntity<>("logoutSuccessfully", HttpStatus.OK);
  }

}
