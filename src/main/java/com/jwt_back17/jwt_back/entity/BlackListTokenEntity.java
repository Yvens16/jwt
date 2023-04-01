package com.jwt_back17.jwt_back.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlackListTokenEntity {
  /**
   * Avoir un système de blacklist permet d'invalider un token au moment ou
   * l'utilisateur se déconnecte
   * Et oui, le fait de se déconnecter en lui même permettrais à une personne
   * malveilllante de toujours utiliser un précédent token
   * Tant que la date d'expiration n'est pas dépassé
   * Et en cas d'alerte l'utilisateur ou le sytème peut invalider un token jsute
   * en se déconnectant
   */

  @Id
  private String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
