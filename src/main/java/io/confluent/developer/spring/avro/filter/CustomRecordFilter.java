//package io.confluent.developer.spring.avro.filter;
//
//import br.com.henrick.avro.Sale;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
//
//import java.util.List;
//
//public class CustomRecordFilter implements RecordFilterStrategy<String, Sale> {
//
//
//    @Override
//    public boolean filter(ConsumerRecord<String, Sale> consumerRecord) {
//        return false;
//    }
//
//    @Override
//    public List<ConsumerRecord<String, Sale>> filterBatch(List<ConsumerRecord<String, Sale>> consumerRecords) {
//        return RecordFilterStrategy.super.filterBatch(consumerRecords);
//    }
//}