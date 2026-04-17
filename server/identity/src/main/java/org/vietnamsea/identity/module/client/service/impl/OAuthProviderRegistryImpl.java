package org.vietnamsea.identity.module.client.service.impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.common.constant.AuthProviderEnum;
import org.vietnamsea.identity.common.helper.EncryptUtil;
import org.vietnamsea.identity.config.security.OAuthConfig;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.client.repository.OAuthClientRepository;
import org.vietnamsea.identity.module.auth.config.OAuth2ProviderConfig;
import org.vietnamsea.identity.module.client.service.OAuthProviderRegistry;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthProviderRegistryImpl implements OAuthProviderRegistry {
  private final OAuthClientRepository oAuthClientRepository;
  private final OAuthConfig config;

  @Override
  public OAuth2ProviderConfig getConfig(AuthProviderEnum provider) {
    var client = oAuthClientRepository.findByProviderAndEnabledTrue(provider)
        .orElseThrow(() -> new ValidationException("oauth provider is not configured or disabled"));

    if (isBlank(client.getClientId()) || isBlank(client.getClientSecretHash()) || isBlank(client.getAuthorizeUrl())
        || isBlank(client.getTokenUri()) || isBlank(client.getUserInfoUrl()) || isBlank(client.getRedirectUrl())) {
      throw new ValidationException("oauth provider config is invalid");
    }

    try {
      var masterKey = Base64.getDecoder().decode(config.getMasterKey());
      var encryptedDataKey = Base64.getDecoder().decode(client.getEncryptedDataKey());
      var dataKey = EncryptUtil.decryptStream(masterKey, encryptedDataKey);
      byte[] encryptedSecret = Base64.getDecoder().decode(client.getClientSecretHash());
      byte[] secretBytes = EncryptUtil.decryptStream(dataKey, encryptedSecret);
      String clientSecret = new String(secretBytes);
      return new OAuth2ProviderConfig(
          client.getClientId(),
          clientSecret,
          client.getAuthorizeUrl(),
          client.getTokenUri(),
          client.getUserInfoUrl(),
          client.getRedirectUrl(),
          parseScopes(client.getScope()));
    } catch (Exception ex) {
      throw new ActionFailedException("Can't login with method " + provider.toString(), ex);
    }
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
