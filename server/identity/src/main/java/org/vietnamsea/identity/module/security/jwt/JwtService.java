package org.vietnamsea.identity.module.security.jwt;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;

public interface JwtService {
  String generateToken(String userId) throws Exception;

  JwtClaimsSet verify(String token);
}
