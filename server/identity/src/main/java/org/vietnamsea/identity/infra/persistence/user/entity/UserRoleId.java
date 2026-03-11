package org.vietnamsea.identity.infra.persistence.user.entity;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserRoleId implements Serializable {
  @Column(name = "user_id")
  private UUID userId;
  @Column(name = "role_id")
  private Long roleId;
}
