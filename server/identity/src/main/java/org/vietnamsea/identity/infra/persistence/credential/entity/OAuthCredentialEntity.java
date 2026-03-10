package org.vietnamsea.identity.infra.persistence.credential.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.vietnamsea.identity.infra.persistence.client.entity.OAuthClientEntity;
import org.vietnamsea.identity.infra.persistence.user.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "oauth_credentials")
@Table(name = "oauth_credentials")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuthCredentialEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;
  @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @ManyToOne(targetEntity = OAuthClientEntity.class, fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "provider_id")
  private OAuthClientEntity provider;
  @Column(name = "provider_user_id", nullable = false)
  private String providerUserId;
  @Column(name = "email")
  private String email;
  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = OffsetDateTime.now();
  }
}
