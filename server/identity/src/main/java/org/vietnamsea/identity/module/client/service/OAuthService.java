package org.vietnamsea.identity.module.client.service;

import org.vietnamsea.identity.module.client.dto.request.OAuthClientRequest;
import org.vietnamsea.identity.module.client.dto.response.OAuthClientResponse;

public interface OAuthService {
    void createOAuthClient(OAuthClientRequest oauthClient);

    OAuthClientResponse getOAuthClientById(Long id);
}
