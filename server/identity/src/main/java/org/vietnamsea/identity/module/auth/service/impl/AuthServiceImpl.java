package org.vietnamsea.identity.module.auth.service.impl;

import java.util.Map;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final JwtService jwtService;
  private final Map<AuthProviderEnum, AuthProvider> providers;

  public AuthResponse authentication(AuthRequest request) {
    var provider = providers.get(request.getProvider());
    if (provider == null) {
      throw new ValidationException("This provider is not supported");
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
