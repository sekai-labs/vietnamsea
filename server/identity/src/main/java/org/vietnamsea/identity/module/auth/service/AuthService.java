package org.vietnamsea.identity.module.auth.service;

import org.vietnamsea.identity.module.auth.dto.request.AuthRequest;
import org.vietnamsea.identity.module.auth.dto.response.AuthResponse;

public interface AuthService {

  AuthResponse authentication(AuthRequest request);

}
