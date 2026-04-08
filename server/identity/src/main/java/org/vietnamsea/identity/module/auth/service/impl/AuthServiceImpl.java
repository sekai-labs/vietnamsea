package org.vietnamsea.identity.module.auth.service.impl;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.common.helper.TokenUtil;
import org.vietnamsea.identity.constant.AuthProviderEnum;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthResponse;
import org.vietnamsea.identity.module.auth.service.AuthProvider;
import org.vietnamsea.identity.module.auth.service.AuthService;
import org.vietnamsea.identity.module.security.jwt.JwtService;

@Service
public class AuthServiceImpl implements AuthService {
  private final JwtService jwtService;
  private final Map<AuthProviderEnum, AuthProvider> providers;

  public AuthServiceImpl(JwtService jwtService, java.util.List<AuthProvider> providers) {
    this.jwtService = jwtService;
    this.providers = providers.stream()
        .collect(Collectors.toMap(AuthProvider::getProvider, Function.identity()));
  }

  public AuthResponse authentication(AuthRequest request) {
    var provider = providers.get(request.getProvider());
    if (provider == null) {
      throw new ValidationException("This provider is not supported");
    }
    if (request.getProvider() != AuthProviderEnum.LOCAL && (request.getCode() == null || request.getCode().isBlank())) {
      throw new ValidationException("authorization code is required for oauth provider");
    }

    var identity = provider.authenticate(request);
    try {
      var accessToken = jwtService.generateToken(identity.getIdentity().toString());
      var refreshToken = TokenUtil.generateRefreshToken();
      return AuthResponse.builder()
          .accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();
    } catch (Exception ex) {
      throw new ActionFailedException("failed to auth with this provider", ex);
    }
  }
}
