package org.vietnamsea.identity.module.role.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionResponse {
  private Long id;
  private String code;
  private String resource;
  private String action;
  private String description;
}
