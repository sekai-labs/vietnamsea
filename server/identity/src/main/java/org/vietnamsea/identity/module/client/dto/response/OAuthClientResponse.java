package org.vietnamsea.identity.module.client.dto.response;

import java.util.Set;

import org.vietnamsea.identity.common.constant.AuthProviderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuthClientResponse {
    private Long id;
    private String name;
    private AuthProviderEnum provider;
    private String clientId;
    private String redirectUrl;
    private String tokenUrl;
    private String userInfoUrl;
    private String authorizeUrl;
    private Set<String> scope;
}
