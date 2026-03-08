package org.vietnamsea.identity.domain.token;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import org.vietnamsea.identity.domain.session.SessionEntity;

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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_id", nullable = false)
  private SessionEntity session;
  @Column(name = "token", nullable = false)
  private String token;
  @Column(name = "expired_at", nullable = false)
  private OffsetDateTime expiresAt;
  @Column(name = "revoked", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
  private Boolean revoked;
  @Column(name = "created_at", nullable = false)
  private OffsetDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = OffsetDateTime.now();
    revoked = false;
  }
}
