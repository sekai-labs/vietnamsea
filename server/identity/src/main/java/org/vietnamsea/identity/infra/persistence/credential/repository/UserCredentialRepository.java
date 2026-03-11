package org.vietnamsea.identity.infra.persistence.credential.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.credential.entity.UserCredentialEntity;
import org.vietnamsea.identity.infra.persistence.user.entity.UserEntity;

@Repository
public interface UserCredentialRepository extends BaseJpaRepository<UserCredentialEntity, UUID> {
  Optional<UserCredentialEntity> findByUserAndChangedAtIsNull(UserEntity user);
}
