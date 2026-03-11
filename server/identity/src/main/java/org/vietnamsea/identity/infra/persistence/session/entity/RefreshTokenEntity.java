package org.vietnamsea.identity.infra.persistence.session.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

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

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;
  @ManyToOne(fetch = FetchType.LAZY, targetEntity = SessionEntity.class, optional = false)
  @JoinColumn(name = "session_id")
  private SessionEntity session;
  @Column(name = "token")
  private String token;
  @Column(name = "expired_at")
  private OffsetDateTime expiresAt;
  @Column(name = "revoked")
  private Boolean revoked;
  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = OffsetDateTime.now();
    revoked = false;
  }
}
