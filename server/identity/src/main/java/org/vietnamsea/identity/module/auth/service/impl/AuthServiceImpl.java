package org.vietnamsea.identity.module.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.exception.AuthException;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;
import org.vietnamsea.identity.module.auth.dto.request.LocalAuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthResponse;
import org.vietnamsea.identity.module.auth.service.AuthService;
import org.vietnamsea.identity.module.security.jwt.JwtService;

import com.nimbusds.jwt.JWT;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Override
  public AuthResponse localAuthentication(LocalAuthRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    var userEntity = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new AuthException("username is not found"));
    try {
      var accessToken = jwtService.generateToken(userEntity.getId().toString());
      return AuthResponse.builder()
          .accessToken(accessToken)
          .build();
    } catch (Exception ex) {
      throw new AuthException("error happen when try login");
    }
  }
}
