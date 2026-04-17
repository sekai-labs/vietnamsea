package org.vietnamsea.identity.infra.messaging;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BaseKafkaProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public BaseKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(String topic, String key, Object value) {
    kafkaTemplate.send(topic, key, value);
  }

  public CompletableFuture<Void> sendAsync(String topic, String key, Object value) {
    return kafkaTemplate.send(topic, key, value).whenComplete((result, ex) -> {
      if (ex != null) {
        log.error("kafka send message failed", ex);
        return;
      }
      var metadata = result.getRecordMetadata();
      log.info(
          "Sent to topic=" + metadata.topic() +
              ", partition=" + metadata.partition() +
              ", offset=" + metadata.offset());
    }).thenApply(result -> null);
  }

  public void sendWithCallback(String topic, String key, Object value) {
    kafkaTemplate.send(topic, key, value)
        .whenComplete((result, ex) -> {
          if (ex != null) {
            log.error("kafka send failed", ex);
          } else {
            var metadata = result.getRecordMetadata();
            log.info(
                "Sent to topic=" + metadata.topic() +
                    ", partition=" + metadata.partition() +
                    ", offset=" + metadata.offset());
          }
        });
  }
}
