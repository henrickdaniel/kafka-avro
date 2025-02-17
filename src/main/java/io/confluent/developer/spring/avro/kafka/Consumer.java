package io.confluent.developer.spring.avro.kafka;

import br.com.henrick.avro.Sale;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
public class Consumer {

  @KafkaListener(topics = "sale")
  public void registeredConsume(ConsumerRecord<String, Sale> saleRecord, Acknowledgment ack) {
    try {
      log.info("Start to consume message saleId {}", saleRecord.value().getSaleId());
      if (StringUtils.containsIgnoreCase("exception", saleRecord.value().getSaleId())) {
        throw new RuntimeException("Failed to process the message");
      }
      ack.acknowledge();
      log.info("Consumed message >>REGISTERED<< with status -> {}", saleRecord.value().getStatus().toString());
    } catch (Exception e) {
      if (isExpiredMessage(saleRecord)) {
        log.info("Sale with id {} exceeded the 2-minute limit", saleRecord.value().getSaleId());
        ack.acknowledge();
      } else {
        log.error("Error processing the message saleId {}", saleRecord.value().getSaleId(), e);
        ack.nack(Duration.ofMinutes(1));
      }
    }
  }

  private boolean isExpiredMessage(ConsumerRecord<String, Sale> consumerRecord) {
    Instant messageCreationInstant = Instant.ofEpochMilli(consumerRecord.timestamp());
    LocalDateTime messageCreationTime = LocalDateTime.ofInstant(messageCreationInstant, ZoneId.of("America/Sao_Paulo"));
    LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    log.info("Message creation time: {} | Current time: {}", messageCreationTime, now);
    return messageCreationTime.isBefore(now.minusMinutes(2));
  }
}
