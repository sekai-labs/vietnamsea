package org.vietnamsea.identity.infra.persistence.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.infra.persistence.user.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
