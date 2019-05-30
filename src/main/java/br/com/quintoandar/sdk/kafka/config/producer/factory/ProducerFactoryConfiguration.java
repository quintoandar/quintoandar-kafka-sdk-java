package br.com.quintoandar.sdk.kafka.config.producer.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.quintoandar.sdk.kafka.config.KafkaProperties;
import br.com.quintoandar.sdk.kafka.config.ObjectMapperConfiguration;

@Configuration
public class ProducerFactoryConfiguration {

    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProducerFactoryConfiguration(KafkaProperties kafkaProperties,
                                        @Qualifier(ObjectMapperConfiguration.KAFKA_OBJECT_MAPPER)
                                                ObjectMapper objectMapper) {
        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public DefaultKafkaProducerFactory<String, ?> producerFactory() {
        JsonSerializer jsonSerializer = new JsonSerializer<>(objectMapper);
        jsonSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<String, JsonSerializer>(producerConfigs(),
                new StringSerializer(), jsonSerializer);
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProducerBootstrapServerConfig());

        return props;
    }

}
