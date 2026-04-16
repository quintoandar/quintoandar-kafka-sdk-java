# quintoandar-kafka-sdk-java

QuintoAndar Kafka SDK for Java Projects

## Changelog

### 0.0.2 (breaking)

`LogRecordFilterStrategy` no longer puts raw message content into MDC. The keys `kafka.value` and `kafka.payload` are removed. Use instead:

- `kafka.valueType`, `kafka.valueLength` — summary of the deserialized value
- `kafka.keyType`, `kafka.keyLength` — summary of the record key

Topic, partition, offset, timestamp, serialized sizes, and headers string representation are unchanged. Update log pipelines or dashboards that relied on full payload in MDC.
