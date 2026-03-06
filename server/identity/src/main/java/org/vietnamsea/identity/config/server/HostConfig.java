package org.vietnamsea.identity.config.server;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@Data
@ConfigurationProperties(prefix = "server")
public class HostConfig {
  private List<String> originAllows;
  private int port;
  private String contextPath;
}
