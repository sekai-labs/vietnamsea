package org.vietnamsea.identity.module.client.service.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vietnamsea.identity.constant.AuthProviderEnum;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.client.repository.OAuthClientRepository;
import org.vietnamsea.identity.module.auth.config.OAuth2ProviderConfig;
import org.vietnamsea.identity.module.client.service.OAuthProviderRegistry;

@Service
@RequiredArgsConstructor
public class OAuthProviderRegistryImpl implements OAuthProviderRegistry {
  private final OAuthClientRepository oAuthClientRepository;

  @Override
  public OAuth2ProviderConfig getConfig(AuthProviderEnum provider) {
    var client = oAuthClientRepository.findByProviderAndEnabledTrue(provider)
        .orElseThrow(() -> new ValidationException("oauth provider is not configured or disabled"));

    if (isBlank(client.getClientId()) || isBlank(client.getClientSecretHash()) || isBlank(client.getAuthorizeUrl())
        || isBlank(client.getTokenUri()) || isBlank(client.getUserInfoUrl()) || isBlank(client.getRedirectUrl())) {
      throw new ValidationException("oauth provider config is invalid");
    }

    return new OAuth2ProviderConfig(
        client.getClientId(),
        client.getClientSecretHash(),
        client.getAuthorizeUrl(),
        client.getTokenUri(),
        client.getUserInfoUrl(),
        client.getRedirectUrl(),
        parseScopes(client.getScope()));
  }

  private Set<String> parseScopes(String rawScope) {
    if (isBlank(rawScope)) {
      return Set.of();
    }
    return Arrays.stream(rawScope.split(","))
        .map(String::trim)
        .filter(scope -> !scope.isEmpty())
        .collect(Collectors.toSet());
  }

  private boolean isBlank(String value) {
    return value == null || value.isBlank();
  }
}
