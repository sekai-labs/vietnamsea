package org.vietnamsea.identity.common.contract;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class BaseEvent {
  private String eventId;
  private String eventType;
  private String source;
  private String traceId;
  private Instant timestamp;
  private String version;
}
