package org.vietnamsea.identity.infra.persistence.credential.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.infra.persistence.credential.entity.TotpCredentialEntity;

@Repository
public interface TotpCredentialRepository extends JpaRepository<TotpCredentialEntity, UUID> {

}
