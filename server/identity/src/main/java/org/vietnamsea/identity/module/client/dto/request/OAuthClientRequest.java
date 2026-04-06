package org.vietnamsea.identity.module.client.dto.request;

import java.util.Set;

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
  @NotNull(message = "clientId must not be empty")
  @NotEmpty(message = "client Id must not be empty")
  private String clientId;
  @NotNull(message = "clientSecret must not be empty")
  @NotEmpty(message = "clientSecret must not be empty")
  private String clientSecretHash;
  @NotEmpty(message = "redirectUrls must not be empty")
  private Set<String> redirectUrls;
  @NotEmpty(message = "redirectUrls must not be empty")
  private Set<String> grantTypes;
  @NotEmpty(message = "scope must not be empty")
  private String scope;
}
