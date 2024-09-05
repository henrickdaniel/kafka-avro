package io.confluent.developer.spring.avro;

import br.com.henrick.avro.Sale;
import io.confluent.developer.spring.avro.domain.SaleRequest;
import io.confluent.developer.spring.avro.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                    /**TODO FIX THIS USING MAPSTRUCT*/
                    /**
                     * 1 - Update request object
                     * 2 - Use mapstruc to transver data
                     * ****/
//                    .setCostumerId(saleRequest.getCostumerId())
//                    .setStatus(saleRequest.getStatus())
                    .setSaleId(saleRequest.getSaleId()).build()
    );
  }

  @PostMapping(value = "/mock")
  public void sendMock() throws InterruptedException {
    consumerService.sendMessages();
  }
}