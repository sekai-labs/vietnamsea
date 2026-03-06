package org.vietnamsea.identity.module.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalAuthRequest {
  private String username;
  private String password;
}
