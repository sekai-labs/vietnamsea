package org.vietnamsea.identity.module.auth.dto.request;

import org.vietnamsea.identity.constant.AuthProviderEnum;

import lombok.Data;

@Data
public class AuthRequest {
  private AuthProviderEnum provider;
  private String code;
  private String email;
  private String password;
}
