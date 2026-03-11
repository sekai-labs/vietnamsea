package org.vietnamsea.identity.infra.persistence.user.entity;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(RolePermissionId.class)
@Table(name = "role_permissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionEntity implements Serializable {
        @EmbeddedId
        private RolePermissionId id;

        @MapsId("roleId")
        @ManyToOne(fetch = FetchType.LAZY, targetEntity = RoleEntity.class)
        @JoinColumn(name = "role_id")
        private RoleEntity role;

        @MapsId("permissionId")
        @ManyToOne(fetch = FetchType.LAZY, targetEntity = PermissionEntity.class)
        @JoinColumn(name = "permission_id")
        private PermissionEntity permission;
}
