package org.vietnamsea.identity.infra.persistence.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.infra.persistence.client.entity.OAuthClientEntity;

@Repository
public interface OAuthClientRepository extends JpaRepository<OAuthClientEntity, Long> {
}
