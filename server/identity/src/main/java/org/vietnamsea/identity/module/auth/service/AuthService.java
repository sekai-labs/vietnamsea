package org.vietnamsea.identity.module.auth.service;

import org.vietnamsea.identity.module.auth.dto.request.LocalAuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthResponse;

public interface AuthService {

  AuthResponse localAuthentication(LocalAuthRequest request);

}
