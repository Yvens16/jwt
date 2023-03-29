package com.jwt_back17.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.jwt_back17.jwt_back.dto.RegisterDto;

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

  public String generateToken(RegisterDto user) {
    String username = user.getUsername();
    Date currentDate = new Date();
    // 30000 30 seconds en millliseconds
    Date expireDate = new Date(currentDate.getTime() + 30000);
    // Implémentation à récupérer d'internet
    String token = Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS512, "secret")
        .compact();
    return token;
  }
}
