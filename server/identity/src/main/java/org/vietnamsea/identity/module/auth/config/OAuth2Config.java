package org.vietnamsea.identity.module.auth.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vietnamsea.identity.common.constant.AuthProviderEnum;
import org.vietnamsea.identity.module.auth.service.AuthProvider;

@Configuration
public class OAuth2Config {
  @Bean
  Map<AuthProviderEnum, AuthProvider> authProviderMap(List<AuthProvider> providers) {
    return providers.stream().collect(Collectors.toMap(AuthProvider::getProvider, Function.identity(), (a, b) -> b));
  }
}
