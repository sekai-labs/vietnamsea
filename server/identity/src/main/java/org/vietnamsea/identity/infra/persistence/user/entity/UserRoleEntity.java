package org.vietnamsea.identity.infra.persistence.user.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@Getter
@Setter
public class UserRoleEntity {
  @EmbeddedId
  private UserRoleId id;

  @MapsId("userId")
  @ManyToOne(targetEntity = UserEntity.class)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @MapsId("roleId")
  @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private RoleEntity role;

}
