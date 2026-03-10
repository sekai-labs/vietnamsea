package org.vietnamsea.identity.infra.persistence.credential.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.infra.persistence.credential.entity.UserCredentialEntity;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredentialEntity, UUID> {

}
