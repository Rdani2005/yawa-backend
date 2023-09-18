package com.rdani2005.yawa.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdani2005.yawa.domain.exception.DomainException;
import com.rdani2005.yawa.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.BiConsumer;

/**
 * The {@code KafkaMessageHelper} class provides utility methods for working with Kafka messages, including
 * deserialization of payloads and creating callback handlers for Kafka message sending.
 */
@Slf4j
@Component
public class KafkaMessageHelper {
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new instance of {@code KafkaMessageHelper} with the specified ObjectMapper.
     *
     * @param objectMapper The ObjectMapper used for payload deserialization.
     */
    public KafkaMessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Deserialize a JSON payload into an object of the specified type.
     *
     * @param payload     The JSON payload to deserialize.
     * @param outputType  The class type to deserialize the payload into.
     * @param <T>         The type of the deserialized object.
     * @return An instance of the specified type containing the deserialized data.
     * @throws DomainException If there is an error during deserialization.
     */
    public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            log.error("Could not read {} object!", outputType.getName(), e);
            throw new DomainException("Could not read " + outputType.getName() + " object!", e);
        }
    }

    /**
     * Create a ListenableFutureCallback for handling Kafka message sending results.
     *
     * @param responseTopicName The name of the Kafka topic to send the message to.
     * @param avroModel         The Avro model to send as a message payload.
     * @param outboxMessage     The outbox message associated with the Kafka message.
     * @param outboxCallback    The callback to handle the outbox status.
     * @param orderId           The order ID associated with the message.
     * @param avroModelName     The name of the Avro model.
     * @param <T>               The type of the Avro model.
     * @param <U>               The type of the outbox message.
     * @return A ListenableFutureCallback for handling Kafka message sending results.
     */
    public <T, U> ListenableFutureCallback<SendResult<String, T>>
    getKafkaCallback(
            String responseTopicName,
            T avroModel,
            U outboxMessage,
            BiConsumer<U, OutboxStatus> outboxCallback,
            String orderId,
            String avroModelName
    ) {
        return new ListenableFutureCallback<SendResult<String, T>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Error while sending {} with message: {} and outbox type: {} to topic {}",
                        avroModelName, avroModel.toString(), outboxMessage.getClass().getName(), responseTopicName, ex);
                outboxCallback.accept(outboxMessage, OutboxStatus.FAILED);
            }

            @Override
            public void onSuccess(SendResult<String, T> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {}" +
                                " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        orderId,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp());
                outboxCallback.accept(outboxMessage, OutboxStatus.COMPLETED);
            }
        };
    }
}
