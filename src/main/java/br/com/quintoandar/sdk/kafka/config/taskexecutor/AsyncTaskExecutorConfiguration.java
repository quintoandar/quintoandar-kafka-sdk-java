package br.com.quintoandar.sdk.kafka.config.taskexecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import br.com.quintoandar.sdk.kafka.config.KafkaProperties;

@Configuration
public class AsyncTaskExecutorConfiguration {

    public final static String KAFKA_JSON_CONSUMER_EXECUTOR = "kafkaJsonConsumerExecutor";
    private final KafkaProperties kafkaProperties;

    @Autowired
    public AsyncTaskExecutorConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean(name = KAFKA_JSON_CONSUMER_EXECUTOR)
    public AsyncListenableTaskExecutor kafkaJsonConsumerExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(kafkaProperties.getConsumerConcurrencyMax());
        executor.setQueueCapacity(0);
        executor.setThreadNamePrefix("kafkaC-");
        return executor;
    }
}
