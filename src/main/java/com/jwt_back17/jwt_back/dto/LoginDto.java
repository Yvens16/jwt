package com.jwt_back17.jwt_back.dto;

public class LoginDto {
  private String username;
  // private String email;
  private String password;
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

  
}
