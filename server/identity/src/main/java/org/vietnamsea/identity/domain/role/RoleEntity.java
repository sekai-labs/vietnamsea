package org.vietnamsea.identity.domain.role;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles", indexes = {
    @Index(name = "idx_roles_code", columnList = "code", unique = true)
})
@Getter
@Setter
public class RoleEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "code", nullable = false)
  private String code;
  @Column(name = "name", nullable = false, length = 30)
  private String name;
  @Column(name = "description", nullable = false, length = 256)
  private String description;
}
