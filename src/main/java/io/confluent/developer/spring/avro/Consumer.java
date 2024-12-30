package io.confluent.developer.spring.avro;

import br.com.henrick.avro.Sale;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class Consumer {

  @KafkaListener(topics = "sale", containerFactory = "registeredListenerContainerFactory")
  public void consumer(ConsumerRecord<String, Sale> record, Acknowledgment acknowledgment) {
    try{
      log.info(String.format("Consumed message >>REGISTRED<<< with status -> %s", record.value().getStatus()));
      acknowledgment.acknowledge();
    } catch (Exception e) {
      if(isMessageExpired(record)){
        acknowledgment.acknowledge();
      }else{
        acknowledgment.nack(Duration.of(40, ChronoUnit.SECONDS));
      }
    }
  }

  private boolean isMessageExpired(ConsumerRecord<String, Sale> record){
    Instant messageCreationInstant = Instant.ofEpochMilli(record.timestamp());
    LocalDateTime messageCreationTime = LocalDateTime.ofInstant(messageCreationInstant, ZoneId.of("UTC"));
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    log.info("messageCreateTime {} now {}", messageCreationTime, now);
    return messageCreationTime.isBefore(now.minusSeconds(20));
  }
}