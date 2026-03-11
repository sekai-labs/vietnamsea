package org.vietnamsea.identity.infra.persistence.session.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.session.entity.SessionEntity;

@Repository
public interface SessionRepository extends BaseJpaRepository<SessionEntity, UUID> {
}
