package io.confluent.developer.spring.avro.config;

import br.com.henrick.avro.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
public class SaleClientConfig {

    @Autowired
    private ConsumerFactory consumerFactory;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Object, Sale>> registeredListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Object, Sale> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
