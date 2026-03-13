package org.vietnamsea.identity.module.auth.dto.request;

import lombok.Data;

@Data
public class AuthRequest {
  private String provider;
  private String code;
  private String email;
  private String password;
}
