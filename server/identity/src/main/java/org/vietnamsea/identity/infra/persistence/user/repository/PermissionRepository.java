package org.vietnamsea.identity.infra.persistence.user.repository;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.user.entity.PermissionEntity;

@Repository
public interface PermissionRepository extends BaseJpaRepository<PermissionEntity, Long> {

}
