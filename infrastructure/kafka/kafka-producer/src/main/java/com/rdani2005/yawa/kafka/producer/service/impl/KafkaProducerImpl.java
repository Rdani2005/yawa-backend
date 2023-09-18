package com.rdani2005.yawa.kafka.producer.service.impl;

import com.rdani2005.yawa.kafka.producer.exception.KafkaProducerException;
import com.rdani2005.yawa.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.PreDestroy;
import java.io.Serializable;

/**
 * The {@code KafkaProducerImpl} class is an implementation of the {@link KafkaProducer} interface that allows
 * sending messages to a Kafka topic using a KafkaTemplate.
 *
 * @param <K> The type of the Kafka message key.
 * @param <V> The type of the Kafka message value.
 */
@Slf4j
@Component
public class KafkaProducerImpl <K extends Serializable, V extends SpecificRecordBase>
        implements KafkaProducer<K, V> {
    private final KafkaTemplate<K, V> kafkaTemplate;
    /**
     * Constructs a new {@code KafkaProducerImpl} with the provided KafkaTemplate.
     *
     * @param kafkaTemplate The KafkaTemplate used for sending messages to Kafka.
     */
    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            ListenableFuture<SendResult<K, V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
            kafkaResultFuture.addCallback(callback);
        } catch (KafkaException e) {
            log.error("Error on kafka producer with key: {}, message: {} and exception: {}", key, message,
                    e.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: " + key + " and message: " + message);
        }
    }

    /**
     * PreDestroy method that closes the Kafka producer by destroying the KafkaTemplate.
     */
    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
