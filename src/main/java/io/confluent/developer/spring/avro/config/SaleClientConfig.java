package io.confluent.developer.spring.avro.config;

import br.com.ApiClient;
import br.com.henrick.SaleApi;
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

    @Value("${sale.url}")
    private String saleUrl;

    @Autowired
    private ConsumerFactory consumerFactory;

    @Bean
    public SaleApi getSale(){
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(saleUrl);
        return new SaleApi(apiClient);
    }

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
}
