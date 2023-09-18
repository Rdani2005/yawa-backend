package com.rdani2005.yawa.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;

/**
 * The {@code KafkaProducer} interface defines a contract for sending messages to a Kafka topic using a Kafka producer.
 *
 * @param <K> The type of the Kafka message key.
 * @param <V> The type of the Kafka message value, typically extending {@link org.apache.avro.specific.SpecificRecordBase}.
 */
public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {
    /**
     * Sends a message with a specified key and value to the given Kafka topic and registers a callback for
     * handling the send result.
     *
     * @param topicName The name of the Kafka topic to send the message to.
     * @param key       The key of the Kafka message.
     * @param message   The message to send, typically extending {@link org.apache.avro.specific.SpecificRecordBase}.
     * @param callback  The callback to handle the send result asynchronously.
     */
    void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback);
}
