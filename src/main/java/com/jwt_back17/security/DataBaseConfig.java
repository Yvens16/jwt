package com.jwt_back17.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:secrets.properties")
public class DataBaseConfig {
  @Value("${myapp.secretKey}")
  private String myappSecretKey;

  public String getMyappSecretKey() {
    return myappSecretKey;
  }

}
