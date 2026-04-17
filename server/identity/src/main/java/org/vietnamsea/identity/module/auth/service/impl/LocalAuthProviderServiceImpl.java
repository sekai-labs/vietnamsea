package org.vietnamsea.identity.module.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.common.constant.AuthProviderEnum;
import org.vietnamsea.identity.exception.AuthException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.credential.repository.UserCredentialRepository;
import org.vietnamsea.identity.infra.persistence.user.repository.UserRepository;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthIdentityResponse;
import org.vietnamsea.identity.module.auth.service.AuthProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalAuthProviderServiceImpl implements AuthProvider {
  private final UserRepository userRepository;
  private final UserCredentialRepository userCredentialRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public AuthProviderEnum getProvider() {
    return AuthProviderEnum.LOCAL;
  }

  @Override
  public AuthIdentityResponse authenticate(AuthRequest request) {
    if (request.getEmail() == null || request.getEmail().isBlank()) {
      throw new ValidationException("email must not be empty");
    }
    if (request.getPassword() == null || request.getPassword().isBlank()) {
      throw new ValidationException("password must not be empty");
    }

    var userEntity = userRepository.findByUsername(request.getEmail())
        .orElseThrow(() -> new AuthException("email is not existed"));
    var credential = userCredentialRepository.findByUserAndChangedAtIsNull(userEntity)
        .orElseThrow(() -> new AuthException("user has no active credential"));

    if (!isPasswordValid(request.getPassword(), credential.getHashPassword())) {
      throw new AuthException("invalid credentials");
    }

    return AuthIdentityResponse.builder()
        .providerId(getProvider().toString())
        .identity(userEntity.getId())
        .email(userEntity.getEmail())
        .name(userEntity.getUsername())
        .build();
  }

  private boolean isPasswordValid(String rawPassword, String hashPassword) {
    try {
      return passwordEncoder.matches(rawPassword, hashPassword);
    } catch (IllegalArgumentException ex) {
      return rawPassword.equals(hashPassword);
    }
  }

}
