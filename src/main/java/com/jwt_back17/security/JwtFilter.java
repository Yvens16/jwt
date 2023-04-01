package com.jwt_back17.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt_back17.jwt_back.repository.BlackListTokenRepository;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  JwtGenerator jwtGenerator;

  @Autowired
  CustomUserDetails customUserDetails;

  @Autowired
  BlackListTokenRepository blackListTokenRepository; 

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = getTokenFromRequest(request);
    try {
      // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/StringUtils.html
      Boolean isTokenInBlackList = blackListTokenRepository.existsByToken(token);
      if (StringUtils.hasText(token) && jwtGenerator.validateToken(token) && !isTokenInBlackList) {
        String username = jwtGenerator.getUserNameFromToken(token);
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
            null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Nous permet d'aller au prochain élément de la chaîne, un autre filter chaîne
    //// si il y'en a un sinon mettre fin à la réalisation de la requête
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  public String getTokenString(String bearerToken) {
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

}
