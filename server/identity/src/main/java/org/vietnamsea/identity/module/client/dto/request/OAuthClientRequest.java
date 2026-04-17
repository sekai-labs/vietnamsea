package org.vietnamsea.identity.module.client.dto.request;

import java.util.Set;

import org.vietnamsea.identity.common.constant.AuthProviderEnum;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuthClientRequest {
  @NotNull(message = "name must not empty")
  @NotEmpty(message = "name must not empty")
  private String name;
  @NotNull(message = "provider must not empty")
  private AuthProviderEnum provider;
  @NotNull(message = "clientId must not empty")
  @NotEmpty(message = "clientId must not empty")
  private String clientId;
  @NotNull(message = "redirectUrl must not empty")
  @NotEmpty(message = "redirectUrl must not empty")
  private String redirectUrl;
  @NotNull(message = "tokenUrl must not empty")
  @NotEmpty(message = "tokenUrl must not empty")
  private String tokenUrl;
  @NotNull(message = "userInfoUrl must not empty")
  @NotEmpty(message = "userInfoUrl must not empty")
  private String userInfoUrl;
  @NotNull(message = "authorizeUrl must not empty")
  @NotEmpty(message = "authorizeUrl must not empty")
  private String authorizeUrl;
  @NotNull(message = "scope must not empty")
  @NotEmpty(message = "scope must not empty")
  private Set<String> scope;
}
