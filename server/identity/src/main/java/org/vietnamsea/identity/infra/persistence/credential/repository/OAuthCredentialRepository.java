package org.vietnamsea.identity.infra.persistence.credential.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.credential.entity.OAuthCredentialEntity;
import org.vietnamsea.identity.infra.persistence.client.entity.OAuthClientEntity;

@Repository
public interface OAuthCredentialRepository extends BaseJpaRepository<OAuthCredentialEntity, UUID> {
  Optional<OAuthCredentialEntity> findByProviderAndProviderUserId(OAuthClientEntity provider,
      String providerUserId);

  Optional<OAuthCredentialEntity> findByProvider_NameAndProviderUserId(String providerName,
      String providerUserId);
}
