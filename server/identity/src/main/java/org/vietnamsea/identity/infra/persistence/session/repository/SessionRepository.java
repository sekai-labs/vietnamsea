package org.vietnamsea.identity.infra.persistence.session.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.infra.persistence.session.entity.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
}
