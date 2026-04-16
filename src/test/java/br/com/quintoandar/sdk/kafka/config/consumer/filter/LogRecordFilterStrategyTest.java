package br.com.quintoandar.sdk.kafka.config.consumer.filter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.MDC;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LogRecordFilterStrategyTest {

    private final LogRecordFilterStrategy strategy = new LogRecordFilterStrategy();

    @Before
    public void clearMdc() {
        MDC.clear();
    }

    @After
    public void tearDown() {
        MDC.clear();
    }

    @Test
    public void filterDoesNotPutRawValueOrPayloadInMdc() {
        final String secret = "secret-payload-content";
        final ConsumerRecord<String, String> record =
                new ConsumerRecord<>("mytopic", 2, 99L, "key1", secret);

        assertFalse(strategy.filter(record));

        final Map<String, String> ctx = MDC.getCopyOfContextMap();
        assertNotNull(ctx);
        assertNull(ctx.get("mytopic_2_kafka.value"));
        assertNull(ctx.get("mytopic_2_kafka.payload"));
        for (String v : ctx.values()) {
            assertFalse(v != null && v.contains(secret));
        }
        assertEquals("String", ctx.get("mytopic_2_kafka.valueType"));
        assertEquals(String.valueOf(secret.length()), ctx.get("mytopic_2_kafka.valueLength"));
        assertEquals("String", ctx.get("mytopic_2_kafka.keyType"));
        assertEquals(String.valueOf("key1".length()), ctx.get("mytopic_2_kafka.keyLength"));
        assertEquals("mytopic", ctx.get("mytopic_2_kafka.topic"));
        assertEquals("99", ctx.get("mytopic_2_kafka.offset"));
    }

    @Test
    public void filterHandlesNullKeyAndValue() {
        final ConsumerRecord<String, String> record =
                new ConsumerRecord<>("t", 0, 0L, null, null);

        assertFalse(strategy.filter(record));

        final Map<String, String> ctx = MDC.getCopyOfContextMap();
        assertNotNull(ctx);
        assertEquals("null", ctx.get("t_0_kafka.keyType"));
        assertEquals("0", ctx.get("t_0_kafka.keyLength"));
        assertEquals("null", ctx.get("t_0_kafka.valueType"));
        assertEquals("0", ctx.get("t_0_kafka.valueLength"));
    }
}
