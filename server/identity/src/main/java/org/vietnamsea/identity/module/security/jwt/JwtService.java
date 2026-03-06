package org.vietnamsea.identity.module.security.jwt;

public interface JwtService {
  String generateToken(String userId) throws Exception;
}
