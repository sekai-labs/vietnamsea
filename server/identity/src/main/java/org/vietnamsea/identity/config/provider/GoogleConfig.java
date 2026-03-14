package org.vietnamsea.identity.config.provider;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "provider.google")
public class GoogleConfig {
  private String clientId;
  private String clientSecret;
  private Set<String> scopes;
  private String redirectUrl;
  private String userProfileEndpoint;
}
