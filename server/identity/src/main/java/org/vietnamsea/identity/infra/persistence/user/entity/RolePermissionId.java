package org.vietnamsea.identity.infra.persistence.user.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {
  @Column(name = "role_id")
  private Long roleId;
  @Column(name = "permission_id")
  private Long permissionId;
}
