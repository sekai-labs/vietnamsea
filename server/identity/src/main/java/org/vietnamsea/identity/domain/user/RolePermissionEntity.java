package org.vietnamsea.identity.domain.user;

import lombok.*;
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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "role_permissions", indexes = {
                @Index(name = "idx_role_permissions_role", columnList = "role_id"),
                @Index(name = "idx_role_permissions_permission", columnList = "permission_id")
}, uniqueConstraints = {
                @UniqueConstraint(name = "uk_role_permission", columnNames = { "role_id", "permission_id" })
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleEntity.class)
        @JoinColumn(name = "role_id", nullable = false)
        private RoleEntity role;

        @ManyToOne(fetch = FetchType.LAZY, targetEntity = PermissionEntity.class)
        @JoinColumn(name = "permission_id", nullable = false)
        private PermissionEntity permission;
}
