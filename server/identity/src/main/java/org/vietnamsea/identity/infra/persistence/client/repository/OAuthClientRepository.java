package org.vietnamsea.identity.infra.persistence.client.repository;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.client.entity.OAuthClientEntity;

@Repository
public interface OAuthClientRepository extends BaseJpaRepository<OAuthClientEntity, Long> {
}
