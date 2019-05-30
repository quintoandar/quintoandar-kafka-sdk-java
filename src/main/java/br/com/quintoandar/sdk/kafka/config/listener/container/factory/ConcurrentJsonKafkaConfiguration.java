package br.com.quintoandar.sdk.kafka.config.listener.container.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.quintoandar.sdk.kafka.config.KafkaProperties;
import br.com.quintoandar.sdk.kafka.config.ObjectMapperConfiguration;
import br.com.quintoandar.sdk.kafka.config.consumer.filter.LogRecordFilterStrategy;
import br.com.quintoandar.sdk.kafka.config.taskexecutor.AsyncTaskExecutorConfiguration;

@Configuration
@EnableKafka
public class ConcurrentJsonKafkaConfiguration {

    private final KafkaProperties kafkaProperties;
    private final ConsumerFactory<Integer, String> consumerFactory;
    private final ObjectMapper objectMapper;
    private final AsyncListenableTaskExecutor asyncListenableTaskExecutor;
    private final LogRecordFilterStrategy logRecordFilterStrategy;

    @Autowired
    public ConcurrentJsonKafkaConfiguration(KafkaProperties kafkaProperties,
                                            ConsumerFactory<Integer, String> consumerFactory,
                                            @Qualifier(ObjectMapperConfiguration.KAFKA_OBJECT_MAPPER) ObjectMapper
                                                        objectMapper,
                                            @Qualifier(AsyncTaskExecutorConfiguration.KAFKA_JSON_CONSUMER_EXECUTOR)
                                                    AsyncListenableTaskExecutor asyncListenableTaskExecutor,
                                            final LogRecordFilterStrategy logRecordFilterStrategy) {
        this.kafkaProperties = kafkaProperties;
        this.consumerFactory = consumerFactory;
        this.objectMapper = objectMapper;
        this.asyncListenableTaskExecutor = asyncListenableTaskExecutor;
        this.logRecordFilterStrategy = logRecordFilterStrategy;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> concurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumerFactory);
        factory.setConcurrency(this.kafkaProperties.getConsumerConcurrencyMax());
        factory.setRecordFilterStrategy(logRecordFilterStrategy);
        factory.setMessageConverter(new StringJsonMessageConverter(objectMapper));
        factory.getContainerProperties().setConsumerTaskExecutor(asyncListenableTaskExecutor);
        return factory;
    }
}
