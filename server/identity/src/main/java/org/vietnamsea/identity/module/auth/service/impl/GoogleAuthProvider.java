package org.vietnamsea.identity.module.auth.service.impl;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.constant.AuthProviderEnum;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthIdentityResponse;
import org.vietnamsea.identity.module.auth.service.AuthProvider;

@Service
public class GoogleAuthProvider implements AuthProvider {

  @Override
  public AuthProviderEnum getProvider() {
    return AuthProviderEnum.GOOGLE;
  }

  @Override
  public AuthIdentityResponse authenticate(AuthRequest request) {
    return null;
  }

}
