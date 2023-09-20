package com.rdani2005.yawa.customer.service.messaging.publisher.kafka;

import com.rdani2005.yawa.customer.service.domain.config.CustomerServiceConfigData;
import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;
import com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher.CustomerCreateMessagePublisher;
import com.rdani2005.yawa.customer.service.messaging.mapper.CustomerMessagingDataMapper;
import com.rdani2005.yawa.kafka.avro.model.CustomerAvroModel;
import com.rdani2005.yawa.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * A Kafka message publisher responsible for publishing CustomerCreatedEvent messages to a Kafka topic.
 */
@Slf4j
@Component
public class CustomerCreatedEventKafkaPublisher implements CustomerCreateMessagePublisher {
    private final CustomerMessagingDataMapper customerMessagingDataMapper;

    private final KafkaProducer<String, CustomerAvroModel> kafkaProducer;

    private final CustomerServiceConfigData customerServiceConfigData;

    /**
     * Constructs a CustomerCreatedEventKafkaPublisher.
     *
     * @param customerMessagingDataMapper    The data mapper for converting events to Avro models.
     * @param kafkaProducer                  The Kafka producer for sending messages.
     * @param customerServiceConfigData      The configuration data for the customer service.
     */
    public CustomerCreatedEventKafkaPublisher(
            CustomerMessagingDataMapper customerMessagingDataMapper,
            KafkaProducer<String, CustomerAvroModel> kafkaProducer,
            CustomerServiceConfigData customerServiceConfigData
    ) {
        this.customerMessagingDataMapper = customerMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.customerServiceConfigData = customerServiceConfigData;
    }

    /**
     * Publishes a CustomerCreatedEvent to a Kafka topic.
     *
     * @param customerCreatedEvent The CustomerCreatedEvent to publish.
     */
    @Override
    public void publish(CustomerCreatedEvent customerCreatedEvent) {
        log.info("Received CustomerCreatedEvent for customer id: {}",
                customerCreatedEvent.getCustomer().getId().getValue());
        try {
            CustomerAvroModel customerAvroModel = customerMessagingDataMapper
                    .customerCreatedEventToCustomerAvroModel(customerCreatedEvent);

            kafkaProducer.send(customerServiceConfigData.getCustomerCreatedTopic(), customerAvroModel.getId(),
                    customerAvroModel,
                    getCallback(customerServiceConfigData.getCustomerCreatedTopic(), customerAvroModel));

            log.info("CustomerCreatedEvent sent to kafka for customer id: {}",
                    customerAvroModel.getId());
        } catch (Exception e) {
            log.error("Error while sending CustomerCreatedEvent to kafka for customer id: {}," +
                    " error: {}", customerCreatedEvent.getCustomer().getId().getValue(), e.getMessage());
        }
    }

    /**
     * Returns a ListenableFutureCallback for handling Kafka message publishing results.
     *
     * @param topicName The name of the Kafka topic.
     * @param message   The Kafka message to publish.
     * @return A ListenableFutureCallback for handling message publishing results.
     */
    private ListenableFutureCallback<SendResult<String, CustomerAvroModel>>
    getCallback(String topicName, CustomerAvroModel message) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, CustomerAvroModel> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            }
        };
    }
}
