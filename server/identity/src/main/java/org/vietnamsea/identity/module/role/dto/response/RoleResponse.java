package org.vietnamsea.identity.module.role.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
  private Long id;
  private String code;
  private String name;
  private String description;
}
