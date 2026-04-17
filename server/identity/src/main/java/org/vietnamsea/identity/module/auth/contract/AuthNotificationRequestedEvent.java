package org.vietnamsea.identity.module.auth.contract;

import java.util.Map;

import org.vietnamsea.identity.common.contract.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuthNotificationRequestedEvent extends BaseEvent {
  private String recipient;
  private AuthNotificationTypeEnum type;
  private Map<String, Object> payload;
}
