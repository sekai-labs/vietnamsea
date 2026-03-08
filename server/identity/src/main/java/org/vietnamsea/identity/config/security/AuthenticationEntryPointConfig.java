package org.vietnamsea.identity.config.security;

import java.io.IOException;

import lombok.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointConfig implements AuthenticationEntryPoint {
  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  public void commence(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull AuthenticationException authException)
      throws IOException, ServletException {
    handlerExceptionResolver.resolveException(request, response, response, authException);
  }

}
