package org.vietnamsea.identity.infra.persistence.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_roles", indexes = {
    @Index(name = "idx_user_roles_user", columnList = "user_id"),
    @Index(name = "idx_user_roles_role", columnList = "role_id")
})
@Getter
@Setter
public class UserRoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(targetEntity = UserEntity.class)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @ManyToOne(targetEntity = RoleEntity.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private RoleEntity role;

}
