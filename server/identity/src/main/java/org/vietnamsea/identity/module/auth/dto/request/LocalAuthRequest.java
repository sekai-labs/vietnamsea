package org.vietnamsea.identity.module.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalAuthRequest {
  @NotEmpty(message = "username is not empty")
  private String username;
  @NotEmpty(message = "password is not empty")
  private String password;
}
