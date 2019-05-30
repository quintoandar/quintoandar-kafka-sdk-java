package br.com.quintoandar.sdk.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaProperties {

    @Value("${quintoandar.kafka.consumer.concurrency.max:1}")
    private int consumerConcurrencyMax;

    @Value("${quintoandar.kafka.consumer.bootstrap.server:localhost}")
    private String consumerBootstrapServerConfig;

    @Value("${quintoandar.kafka.producer.bootstrap.server:localhost}")
    private String producerBootstrapServerConfig;

    @Value("${quintoandar.kafka.consumer.group.id:invalidGroup}")
    private String groupId;

    public String getConsumerBootstrapServerConfig() {
        return consumerBootstrapServerConfig;
    }

    public String getProducerBootstrapServerConfig() {
        return producerBootstrapServerConfig;
    }

    public int getConsumerConcurrencyMax() {
        return consumerConcurrencyMax;
    }

    public String getGroupId() {
        return groupId;
    }
}
