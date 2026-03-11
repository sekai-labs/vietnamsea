package org.vietnamsea.identity.infra.persistence.user.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {
  private Long role;
  private Long permission;
}
