package org.vietnamsea.identity.module.client.dto.response;

import java.util.Set;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OAuthClientResponse {
    private Long id;
    private String name;
    private String clientId;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private Set<String> grantTypes;
}
