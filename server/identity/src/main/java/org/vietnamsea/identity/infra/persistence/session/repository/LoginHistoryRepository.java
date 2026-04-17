package org.vietnamsea.identity.infra.persistence.session.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.vietnamsea.identity.common.repository.BaseJpaRepository;
import org.vietnamsea.identity.infra.persistence.session.entity.LoginHistoryEntity;

@Repository
public interface LoginHistoryRepository extends BaseJpaRepository<LoginHistoryEntity, UUID> {
  List<LoginHistoryEntity> findByUser_IdOrderByCreatedAtDesc(UUID userId);

  Optional<LoginHistoryEntity> findTop1ByUser_IdOrderByCreatedAtDesc(UUID userId);

  Optional<LoginHistoryEntity> findFirstByUser_IdOrderByCreatedAtDesc(UUID userId);
}
