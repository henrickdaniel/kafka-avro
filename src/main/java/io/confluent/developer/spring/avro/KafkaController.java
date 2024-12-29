package io.confluent.developer.spring.avro;

import br.com.henrick.avro.Costumer;
import br.com.henrick.avro.Log;
import br.com.henrick.avro.Sale;
import br.com.henrick.avro.Status;
import io.confluent.developer.spring.avro.domain.SaleRequest;
import io.confluent.developer.spring.avro.service.ConsumerService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/sale")
public class KafkaController {

  private final Producer producer;

  @Autowired
  KafkaController(Producer producer) {
    this.producer = producer;
  }

  @Autowired
  private ConsumerService consumerService;


  @PostMapping(value = "/publish")
  public void sendMessageToKafkaTopic(@RequestBody SaleRequest saleRequest) {



    this.producer.sendMessage(
            Sale.newBuilder()
                    .setSaleId(saleRequest.getSaleId())
                    .setCostumer(Costumer.newBuilder()
                            .setName(saleRequest.getCostumer().getName())
                            .setDateOfBirth(saleRequest.getCostumer().getBirthDate())
                            .build())
                    .setStatus(Status.valueOf(saleRequest.getStatus()))
                    .setLogs(Arrays.asList(
                            Log.newBuilder()
                                    .setStatus(Status.valueOf(saleRequest.getStatus()))
                                    .setDateTimeUtc(LocalDateTime.now())
                                    .build())
                    )
                    .build());
  }

  @PostMapping(value = "/mock")
  public void sendMock() throws InterruptedException {
    consumerService.sendMessages();
  }
}