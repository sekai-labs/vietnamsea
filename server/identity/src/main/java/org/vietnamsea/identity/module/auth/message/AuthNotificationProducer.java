package org.vietnamsea.identity.module.auth.message;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.infra.messaging.BaseKafkaProducer;
import org.vietnamsea.identity.module.auth.contract.AuthNotificationRequestedEvent;
import org.vietnamsea.identity.module.auth.contract.AuthNotificationTypeEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthNotificationProducer {
  private final BaseKafkaProducer producer;

  public void sendOtp(String email, String otp, String username) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("otp", otp);
    payload.put("expireMinutes", 5);
    payload.put("username", username);
    var event = AuthNotificationRequestedEvent.builder()
        .eventId(UUID.randomUUID().toString())
        .eventType("notification.send")
        .source("identity-service")
        .traceId(UUID.randomUUID().toString())
        .timestamp(Instant.now())
        .version("v1")
        .recipient(email)
        .type(AuthNotificationTypeEnum.OTP)
        .payload(payload)
        .build();
    producer.sendAsync("notification.send", email, event);
  }

  public void sendLoginWarning(String username, String email, Map<String, Double> coordinates) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("email", email);
    payload.put("username", username);
    payload.put("login", Instant.now());
    payload.put("latitude", coordinates.get("latitude"));
    payload.put("longitude", coordinates.get("longitude"));
    var event = AuthNotificationRequestedEvent.builder()
        .eventId(UUID.randomUUID().toString())
        .eventType("notification.send")
        .source("identity-service")
        .traceId(UUID.randomUUID().toString())
        .timestamp(Instant.now())
        .version("v1")
        .recipient(email)
        .type(AuthNotificationTypeEnum.LOGIN_WARNING)
        .payload(payload)
        .build();
    producer.sendAsync("notification.send", email, event);
  }
}
