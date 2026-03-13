package org.vietnamsea.identity.module.auth.service.impl;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.constant.AuthProviderEnum;
import org.vietnamsea.identity.exception.AuthException;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthIdentityResponse;
import org.vietnamsea.identity.module.auth.service.AuthProvider;

@Service
public class LocalAuthProvider implements AuthProvider {
  private UserRepository userRepository;

  @Override
  public AuthProviderEnum getProvider() {
    return AuthProviderEnum.LOCAL;
  }

  @Override
  public AuthIdentityResponse authenticate(AuthRequest request) {
    var userEntity = userRepository.findByUsername(request.getEmail())
        .orElseThrow(() -> new AuthException("email is not existed"));

    return AuthIdentityResponse.builder()
        .providerId(getProvider().toString())
        .identity(userEntity.getId().toString())
        .email(userEntity.getEmail())
        .name(userEntity.getUsername())
        .build();
  }

}
