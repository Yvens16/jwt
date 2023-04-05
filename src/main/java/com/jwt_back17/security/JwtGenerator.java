package com.jwt_back17.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jwt_back17.jwt_back.dto.RegisterDto;

import io.jsonwebtoken.Claims;
/* 
Voir pom.xml au niveau de 
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>
*/
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @Component permet à spring de detecter notre class custom
 *            et on pourra donc l'injecter ou l'on en a besoin grâce
 *            à @Autowired
 */
@Component
public class JwtGenerator {

  // ADD_ROLE_TO_USER_AND_TOKEN
  @Autowired
  CustomUserDetails customUserDetails;

  public String generateToken(RegisterDto user) {

    // claims.put("role", role); // add role claim

    // Map<String, String> claims = new HashMap<String, String>();
    // claims.put("role", "user");

    String username = user.getUsername();

    // ADD_ROLE_TO_USER_AND_TOKEN
    UserDetails userDetails = customUserDetails.loadUserByUsername(username);
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("role", userDetails.getAuthorities());
    Date currentDate = new Date();
    // 30000 30 seconds en millliseconds
    Date expireDate = new Date(currentDate.getTime() + 30000000);
    // Implémentation à récupérer d'internet
    String token = Jwts.builder()
        .setClaims(claims)
        // .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS512, "secret")
        .compact();
    return token;
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
      return true;
    } catch (Exception err) {
      throw new AuthenticationCredentialsNotFoundException("Le token n'est pas bon ou est expiré");
    }
  }

  public String getUserNameFromToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey("secret")
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

}
