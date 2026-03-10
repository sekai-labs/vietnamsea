package org.vietnamsea.identity.infra.persistence.session.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.infra.persistence.session.entity.LoginHistoryEntity;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistoryEntity, UUID> {
}
