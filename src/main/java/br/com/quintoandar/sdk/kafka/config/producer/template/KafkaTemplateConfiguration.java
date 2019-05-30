package br.com.quintoandar.sdk.kafka.config.producer.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaTemplateConfiguration {

    private final DefaultKafkaProducerFactory<String, ?> producerFactory;

    @Autowired
    public KafkaTemplateConfiguration(DefaultKafkaProducerFactory<String, ?> producerFactory) {
        this.producerFactory = producerFactory;
    }

    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate() {
        return new KafkaTemplate<>(this.producerFactory);
    }
}
