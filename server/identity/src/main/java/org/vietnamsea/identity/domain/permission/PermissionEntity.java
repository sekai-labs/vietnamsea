package org.vietnamsea.identity.domain.permission;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "permissions", indexes = {
    @Index(name = "idx_permissions_code", columnList = "code")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "code")
  private String code;
  @Column(name = "resource")
  private String resource;
  @Column(name = "action", nullable = false)
  private String action;
  @Column(name = "description")
  private String description;
}
