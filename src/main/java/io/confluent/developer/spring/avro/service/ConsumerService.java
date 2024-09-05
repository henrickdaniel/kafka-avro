package io.confluent.developer.spring.avro.service;


import br.com.henrick.SaleApi;
import br.com.henrick.avro.Costumer;
import br.com.henrick.avro.Sale;
import br.com.henrick.avro.Status;
import br.com.henrick.avro.Log;
import com.github.javafaker.Faker;
import io.confluent.developer.spring.avro.Producer;
import io.confluent.developer.spring.avro.domain.StatusEnum;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

@Service
public class ConsumerService {

    @Autowired
    private SaleApi saleApi;

    @Autowired
    private Producer producer;

    public void sendMessages() throws InterruptedException {

        Random random = new Random();
        for (int i = 0; i <10; i ++) {
            Thread.sleep(1000);

            this.producer.sendMessage(
                    Sale.newBuilder()
                            .setCostumer(Costumer.newBuilder()
                                    .setName(Faker.instance().funnyName().name())
                                    .setDateOfBirth(LocalDate.now())
                                    .build())
                            .setSaleId(Faker.instance().idNumber().valid())
                            .setStatus(Status.values()[random.nextInt(StatusEnum.values().length)])
                            .setLogs(Arrays.asList(Log.newBuilder()
                                    .setDateTimeUtc(LocalDateTime.now())
                                    .setStatus(Status.REGISTERED)
                                    .build(), Log.newBuilder()
                                    .setDateTimeUtc(LocalDateTime.now())
                                    .setStatus(Status.CARRIER_PENDANT)
                                    .build()))
                            .build()
            );
        }
    }
}