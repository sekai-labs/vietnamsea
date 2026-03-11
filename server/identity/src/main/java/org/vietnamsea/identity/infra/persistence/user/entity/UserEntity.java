package org.vietnamsea.identity.infra.persistence.user.entity;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.UUID;

import org.vietnamsea.identity.common.entity.BaseSoftDeleteEntity;
import org.vietnamsea.identity.infra.persistence.session.entity.SessionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseSoftDeleteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;
  @Column(name = "username")
  private String username;
  @Column(name = "email")
  private String email;
  @Column(name = "created_at")
  private OffsetDateTime createdAt;
  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;
  @Column(name = "email_verified")
  private Boolean emailVerified;
  @Column(name = "status")
  private String status;
  @Column(name = "locked")
  private Boolean locked;
  @Column(name = "disabled")
  private Boolean disabled;
  @Column(name = "last_login_at")
  private Timestamp lastLoginAt;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
  private Collection<SessionEntity> sessions;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Collection<UserRoleEntity> roles;

  @PrePersist
  protected void onCreate() {
    var now = OffsetDateTime.now();
    createdAt = now;
    updatedAt = now;
    locked = false;
    disabled = false;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = OffsetDateTime.now();
  }
}
