package com.rdani2005.yawa.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.util.List;
/**
 * The {@code KafkaConsumer} interface represents a contract for consuming Kafka messages of a specific type.
 *
 * @param <T> The type of Kafka messages to be consumed, which should extend {@code SpecificRecordBase}.
 */
public interface KafkaConsumer<T extends SpecificRecordBase>  {
    /**
     * Receives and processes a batch of Kafka messages along with associated metadata.
     *
     * @param messages   A list of Kafka messages of type {@code T}.
     * @param keys       A list of message keys.
     * @param partitions A list of partition numbers from which the messages were consumed.
     * @param offsets    A list of message offsets.
     */
    void receive(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
