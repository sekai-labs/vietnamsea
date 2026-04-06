package org.vietnamsea.identity.common.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuthClientConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String userProfileEndpoint;
}
