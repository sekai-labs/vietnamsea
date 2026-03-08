package org.vietnamsea.identity.common.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseSoftDeleteEntity {
  @Column(name = "deleted_at")
  private OffsetDateTime deletedAt;

  public void softDelete() {
    this.deletedAt = OffsetDateTime.now();
  }
}
