package org.vietnamsea.identity.module.role.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleRequest {
  @NotNull(message = "code should not be empty")
  @NotEmpty(message = "code should not be empty")
  private String code;
  @NotNull(message = "name should not be empty")
  @NotEmpty(message = "name should not be empty")
  private String name;
  @NotNull(message = "description")
  @NotEmpty(message = "description")
  private String description;

  private List<Long> permissionIds;
}
