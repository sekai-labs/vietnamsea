package org.vietnamsea.identity.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {
  private String privateKeyPath;
  private String publicKeyPath;
  private String keyId;
  private String issuer;
  private long maxAge;
}
