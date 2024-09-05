package io.confluent.developer.spring.avro;

import br.com.henrick.avro.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog(topic = "Producer Logger")
public class Producer {

  @Value("${topic.name}")
  private String TOPIC;

  private final KafkaTemplate<String, Sale> kafkaTemplate;

  @Autowired
  public Producer(KafkaTemplate<String, Sale> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(Sale sale) {
    this.kafkaTemplate.send(this.TOPIC, sale.getSaleId(), sale);
    log.info(String.format("Produced sale -> %s", sale));
  }
}
