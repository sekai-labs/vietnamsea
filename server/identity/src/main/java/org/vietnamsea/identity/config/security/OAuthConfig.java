package org.vietnamsea.identity.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.oauth2")
public class OAuthConfig {
  private String masterKey;
}
