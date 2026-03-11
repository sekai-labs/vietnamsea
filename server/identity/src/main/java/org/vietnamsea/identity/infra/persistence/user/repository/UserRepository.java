package org.vietnamsea.identity.infra.persistence.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.user.entity.UserEntity;

@Repository
public interface UserRepository extends BaseJpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByUsername(String username);
}
