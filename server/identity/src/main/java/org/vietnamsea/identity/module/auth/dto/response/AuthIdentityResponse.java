package org.vietnamsea.identity.module.auth.dto.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthIdentityResponse {
  private UUID identity;
  private String providerId;
  private String email;
  private String name;
}
