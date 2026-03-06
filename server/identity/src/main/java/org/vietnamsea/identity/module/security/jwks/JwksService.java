package org.vietnamsea.identity.module.security.jwks;

import java.util.Map;

public interface JwksService {
  Map<String, Object> getKeys();
}
