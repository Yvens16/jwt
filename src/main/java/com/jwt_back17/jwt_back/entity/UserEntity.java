package com.jwt_back17.jwt_back.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * 
 * @Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;

  @OneToMany(mappedBy = "buyer")
  private List<Transaction> boughtTransactions;

  @OneToMany(mappedBy = "seller")
  private List<Transaction> soldTransactions;

  // getters and setters
}
 */


@Entity
public class UserEntity {
  

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String username;
  private String email;
  private String password;
  private String role;
  
  @OneToMany(mappedBy = "buyer")
  private List<Transaction> boughtTransactions;

  @OneToMany(mappedBy = "seller")
  private List<Transaction> soldTransactions;
  public UserEntity() {}

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<Transaction> getBoughtTransactions() {
    return boughtTransactions;
  }

  public List<Transaction> getSoldTransactions() {
    return soldTransactions;
  }
}
