package org.vietnamsea.identity.domain.session;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.vietnamsea.identity.domain.user.UserEntity;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sessions")
@Getter
@Setter
public class SessionEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;
  @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @Column(name = "device_id")
  private String deviceId;
  @Column(name = "ip_address")
  private String ipAddress;
  @Column(name = "user_agent")
  private String userAgent;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;
  @Column(name = "expires_at", nullable = false)
  private OffsetDateTime expiresAt;
  @Column(name = "revoked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean revoked;

  @PrePersist
  protected void onCreate() {
    var now = OffsetDateTime.now();
    createdAt = now;
    revoked = false;
  }
}
