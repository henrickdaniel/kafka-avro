package io.confluent.developer.spring.avro;

import br.com.henrick.avro.Sale;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {

  @KafkaListener(topics = "sale", containerFactory = "registeredListenerContainerFactory")
  public void registeredConsume(ConsumerRecord<String, Sale> record) {
    log.info(String.format("Consumed message >>REGISTRED<<< with status -> %s", record.value().getStatus()));
  }

  @KafkaListener(topics = "sale", containerFactory = "paymentDoneListenerContainerFactory")
  public void registeredconsume(ConsumerRecord<String, Sale> record) {
    log.info(String.format("Consumed message >>PAYMENT_DONE<<< with status -> %s", record.value().getStatus()));
  }
}