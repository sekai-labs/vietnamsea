package org.vietnamsea.identity.module.auth.dto.request;

import lombok.Data;

@Data
public class GoogleAuthRequest {
  private String code;
  private String type;
}
