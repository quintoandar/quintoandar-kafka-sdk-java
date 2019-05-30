package br.com.quintoandar.sdk.kafka.config.listener.container.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.quintoandar.sdk.kafka.config.consumer.filter.LogRecordFilterStrategy;

@Configuration
@EnableKafka
public class SimpleJsonKafkaConfiguration {

    private final ObjectMapper objectMapper;
    private final ConsumerFactory<Integer, String> consumerFactory;
    private final LogRecordFilterStrategy logRecordFilterStrategy;

    @Autowired
    public SimpleJsonKafkaConfiguration(ObjectMapper objectMapper,
                                        ConsumerFactory<Integer, String> consumerFactory,
                                        final LogRecordFilterStrategy logRecordFilterStrategy) {
        this.objectMapper = objectMapper;
        this.consumerFactory = consumerFactory;
        this.logRecordFilterStrategy = logRecordFilterStrategy;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumerFactory);
        factory.setRecordFilterStrategy(logRecordFilterStrategy);
        factory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        return factory;
    }
}
