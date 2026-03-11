package org.vietnamsea.identity.infra.persistence.session.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

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

@Entity(name = "login_histories")
@Table(name = "login_histories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginHistoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "success")
    private Boolean success;
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = OffsetDateTime.now();
        if (success == null) {
            success = true;
        }
    }
}
