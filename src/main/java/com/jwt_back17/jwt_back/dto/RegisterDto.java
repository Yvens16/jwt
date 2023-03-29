package com.jwt_back17.jwt_back.dto;

/**
 * Data Transfer Object permet de contractualisé les données passer en paramètre d'un call
 * Agit un peu comme une interface en typescript
 */

public class RegisterDto {
  private String username;
  private String password;
  private String email;
  // private String role;

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
  /*
   * public String getRole() {
   * return role;
   * }
   * public void setRole(String role) {
   * this.role = role;
   * }
   */
}
