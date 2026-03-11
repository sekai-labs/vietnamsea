package org.vietnamsea.identity.infra.persistence.user.entity;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(RolePermissionId.class)
@Table(name = "role_permissions", indexes = {
                @Index(name = "idx_role_permissions_permission", columnList = "permission_id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionEntity {
        @Id
        @ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleEntity.class)
        @JoinColumn(name = "role_id", nullable = false)
        private RoleEntity role;

        @Id
        @ManyToOne(fetch = FetchType.LAZY, targetEntity = PermissionEntity.class)
        @JoinColumn(name = "permission_id", nullable = false)
        private PermissionEntity permission;
}
