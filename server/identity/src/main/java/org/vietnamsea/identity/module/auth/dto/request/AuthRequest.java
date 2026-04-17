package org.vietnamsea.identity.module.auth.dto.request;

import org.vietnamsea.identity.common.constant.AuthProviderEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
  @NotNull(message = "provider must not null")
  private AuthProviderEnum provider;
  private String code;
  private String email;
  private String password;
}
