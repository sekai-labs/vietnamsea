package org.vietnamsea.identity.module.auth.service;

import org.vietnamsea.identity.constant.AuthProviderEnum;
import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthIdentityResponse;

public interface AuthProvider {
  AuthProviderEnum getProvider();

  AuthIdentityResponse authenticate(AuthRequest request);
}
