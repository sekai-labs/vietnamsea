package org.vietnamsea.identity.infra.persistence.credential.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.credential.entity.OAuthCredentialEntity;

@Repository
public interface OAuthCredentialRepository extends BaseJpaRepository<OAuthCredentialEntity, UUID> {

}
