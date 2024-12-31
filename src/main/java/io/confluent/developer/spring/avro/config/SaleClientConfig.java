package io.confluent.developer.spring.avro.config;

import br.com.henrick.avro.Sale;
import br.com.henrick.avro.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class SaleClientConfig {

    @Autowired
    private ConsumerFactory consumerFactory;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, Sale>> registeredListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Sale> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordFilterStrategy(record -> {
            if(record.value().getStatus().equals(Status.REGISTERED)){
                return false;
            }
           return true;
        });
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, Sale>> paymentDoneListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Sale> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordFilterStrategy(record -> {
            if(record.value().getStatus().equals(Status.PAYMENT_DONE)){
                return false;
            }
            return true;
        });
        return factory;
    }
}
