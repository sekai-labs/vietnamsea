package org.vietnamsea.identity.module.auth.config;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2ProviderConfig {
  private String clientId;
  private String clientSecret;
  private String authorizeUrl;
  private String tokenUrl;
  private String userInfoUrl;
  private String redirectUrl;
  private Set<String> scope;
}
