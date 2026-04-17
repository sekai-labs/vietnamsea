package org.vietnamsea.identity.module.client.service;

import org.vietnamsea.identity.common.constant.AuthProviderEnum;
import org.vietnamsea.identity.module.auth.config.OAuth2ProviderConfig;

public interface OAuthProviderRegistry {
  OAuth2ProviderConfig getConfig(AuthProviderEnum provider);
}
