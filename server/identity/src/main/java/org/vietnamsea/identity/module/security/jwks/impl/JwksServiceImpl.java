package org.vietnamsea.identity.module.security.jwks.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.module.security.jwks.JwksService;
import org.vietnamsea.identity.module.security.key.JwtKeyProvider;

import com.nimbusds.jose.jwk.JWKSet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwksServiceImpl implements JwksService {
  private final JwtKeyProvider jwtKeyProvider;

  @Override
  public Map<String, Object> getKeys() {
    JWKSet jwkSet = new JWKSet(jwtKeyProvider.getPublicJwk());
    return jwkSet.toJSONObject();
  }
}
