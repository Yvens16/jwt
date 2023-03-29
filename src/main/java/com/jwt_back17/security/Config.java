package com.jwt_back17.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class Config {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http.authorizeHttpRequests((auth) -> {
    // try {
    // auth
    // .anyRequest().authenticated()
    // .and().formLogin()
    // .and().httpBasic();
    // } catch (Exception e) {

    // e.printStackTrace();
    // }
    // });
    // Cross-Site Request Forgery (CSRF) is enabled by default in spring security
    // configuration
    /**
     * Let's consider the following GET request used by a logged-in user to transfer
     * money to a specific bank account 1234:
     * 
     * GET http://bank.com/transfer?accountNo=1234&amount=100
     * Copy
     * If the attacker wants to transfer money from a victim's account to his own
     * account instead — 5678 — he needs to make the victim trigger the request:
     * 
     * GET http://bank.com/transfer?accountNo=5678&amount=1000
     * Since we use the jwt token, we don't need the csrf token to be enabled so we
     * can disable it
     */
    http.cors().and().csrf().disable();
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
