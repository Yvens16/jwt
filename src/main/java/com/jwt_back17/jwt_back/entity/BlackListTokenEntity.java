package com.jwt_back17.jwt_back.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlackListTokenEntity {

  @Id
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
