package br.com.quintoandar.sdk.kafka.config.producer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import br.com.quintoandar.sdk.kafka.TriConsumer;

@Component
public class KafkaProducerSender<T> {

    private final KafkaTemplate<String, ? super T> kafkaTemplate;

    @Autowired
    public KafkaProducerSender(KafkaTemplate<String, ? super T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topic, T message) {
        Consumer<SendResult<String, ? super T>> handleOnSuccess = msg -> {
            return;
        };
        TriConsumer<Throwable, String, T> handleOnFailure = (throwable, top, msg) -> {
            return;
        };

        this.doSend(topic, message, handleOnSuccess, handleOnFailure);
    }

    public void send(String topic,
                     T message,
                     Consumer<SendResult<String, ? super T>> handleOnSuccess,
                     TriConsumer<Throwable, String, T> handleOnFailure) {
        this.doSend(topic, message, handleOnSuccess, handleOnFailure);
    }

    private void doSend(String topic,
                        T message,
                        Consumer<SendResult<String, ? super T>> handleOnSuccess,
                        TriConsumer<Throwable, String, T> handleOnFailure) {
        ListenableFuture<? extends SendResult<String, ? super T>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, ? super T>>() {

            @Override
            public void onSuccess(SendResult<String, ? super T> result) {
                handleOnSuccess.accept(result);
            }


            @Override
            public void onFailure(final Throwable throwable) {
                handleOnFailure.accept(throwable, topic, message);
            }
        });
    }
}
