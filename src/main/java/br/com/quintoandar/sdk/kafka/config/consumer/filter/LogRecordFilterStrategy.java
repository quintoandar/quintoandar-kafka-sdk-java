package br.com.quintoandar.sdk.kafka.config.consumer.filter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

@Component
public class LogRecordFilterStrategy implements RecordFilterStrategy {

    private static final String KAFKA_TOPIC = "kafka.topic";
    private static final String KAFKA_PARTITION = "kafka.partition";
    private static final String KAFKA_OFFSET = "kafka.offset";
    private static final String KAFKA_TIMESTAMP = "kafka.timestamp";
    private static final String KAFKA_TMESTAMP_TYPE = "kafka.timestampType";
    private static final String KAFKA_SERIALIZED_KEY_SIZE = "kafka.serializedKeySize";
    private static final String KAFKA_SERIALIZED_VALUE_SIZE = "kafka.serializedValueSize";
    private static final String KAFKA_HEADERS = "kafka.headers";
    private static final String KAFKA_KEY = "kafka.key";
    private static final String KAFKA_VALUE = "kafka.value";
    private static final String KAFKA_PAYLOAD = "kafka.payload";

    @Override
    public boolean filter(final ConsumerRecord consumerRecord) {
        final String baseKey = consumerRecord.topic()
                               + "_"
                               + consumerRecord.partition();

        MDC.put(key(baseKey, KAFKA_TOPIC), consumerRecord.topic());
        MDC.put(key(baseKey, KAFKA_PARTITION), String.valueOf(consumerRecord.partition()));
        MDC.put(key(baseKey, KAFKA_OFFSET), String.valueOf(consumerRecord.offset()));
        MDC.put(key(baseKey, KAFKA_TIMESTAMP), String.valueOf(consumerRecord.timestamp()));
        MDC.put(key(baseKey, KAFKA_TMESTAMP_TYPE), String.valueOf(consumerRecord.timestampType()));
        MDC.put(key(baseKey, KAFKA_SERIALIZED_KEY_SIZE), String.valueOf(consumerRecord.serializedKeySize()));
        MDC.put(key(baseKey, KAFKA_SERIALIZED_VALUE_SIZE), String.valueOf(consumerRecord.serializedValueSize()));
        MDC.put(key(baseKey, KAFKA_HEADERS), String.valueOf(consumerRecord.headers()));
        MDC.put(key(baseKey, KAFKA_KEY), String.valueOf(consumerRecord.key()));
        MDC.put(key(baseKey, KAFKA_VALUE), String.valueOf(consumerRecord.value()));
        MDC.put(key(baseKey, KAFKA_PAYLOAD), String.valueOf(consumerRecord));
        return false;
    }

    private String key(String base, String append) {
        return base + "_" + append;
    }
}
