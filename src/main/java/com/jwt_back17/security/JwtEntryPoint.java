package com.jwt_back17.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    System.out.println(("@@@@@@@@@@@@@@@@@@@TEEEESSSSSSTTTTT MIDDLEWARE: " + authException.getMessage()));
    // response.setContentType("application/json");
    // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
  }

}
