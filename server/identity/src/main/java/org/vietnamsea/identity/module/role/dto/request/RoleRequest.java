package org.vietnamsea.identity.module.role.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class RoleRequest {
  @NotNull(message = "")
  @NotEmpty(message = "")
  private String code;
  @NotNull
  @NotEmpty
  private String name;
  @NotNull
  @NotEmpty
  private String description;

  private List<Long> permissionIds;
}
