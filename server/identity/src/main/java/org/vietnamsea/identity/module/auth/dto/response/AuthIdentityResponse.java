package org.vietnamsea.identity.module.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthIdentityResponse {
  private String identity;
  private String providerId;
  private String email;
  private String name;
}
