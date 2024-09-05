package io.confluent.developer.spring.avro;

import br.com.henrick.avro.Sale;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog(topic = "Consumer Logger")
public class Consumer {

  @Value("${topic.name}")
  private String topicName;

  @KafkaListener(topics = "sale", containerFactory = "registeredListenerContainerFactory")
  public void registeredConsume(ConsumerRecord<String, Sale> record) {
    log.info(String.format("Consumed message >>REGISTRED<<< with status -> %s", record.value().getStatus()));
  }

  @KafkaListener(topics = "sale", containerFactory = "paymentDoneListenerContainerFactory")
  public void registeredconsume(ConsumerRecord<String, Sale> record) {
    log.info(String.format("Consumed message >>PAYMENT_DONE<<< with status -> %s", record.value().getStatus()));
  }
}