package com.rdani2005.yawa.customer.service.messaging.publisher.kafka;

import com.rdani2005.yawa.customer.service.domain.config.CustomerServiceConfigData;
import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;
import com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher.CustomerDeletedMessagePublisher;
import com.rdani2005.yawa.customer.service.messaging.mapper.CustomerMessagingDataMapper;
import com.rdani2005.yawa.kafka.avro.model.CustomerAvroModel;
import com.rdani2005.yawa.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * A Kafka message publisher responsible for publishing CustomerDeletedEvent messages to a Kafka topic.
 */
@Slf4j
@Component
public class CustomerDeletedEventKafkaPublisher implements CustomerDeletedMessagePublisher {
    private final CustomerMessagingDataMapper customerMessagingDataMapper;

    private final KafkaProducer<String, CustomerAvroModel> kafkaProducer;

    private final CustomerServiceConfigData customerServiceConfigData;

    /**
     * Constructs a CustomerDeletedEventKafkaPublisher.
     *
     * @param customerMessagingDataMapper    The data mapper for converting events to Avro models.
     * @param kafkaProducer                  The Kafka producer for sending messages.
     * @param customerServiceConfigData      The configuration data for the customer service.
     */
    public CustomerDeletedEventKafkaPublisher(
            CustomerMessagingDataMapper customerMessagingDataMapper,
            KafkaProducer<String, CustomerAvroModel> kafkaProducer,
            CustomerServiceConfigData customerServiceConfigData
    ) {
        this.customerMessagingDataMapper = customerMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.customerServiceConfigData = customerServiceConfigData;
    }

    /**
     * Publishes a CustomerDeletedEvent to a Kafka topic.
     *
     * @param customerDeletedEvent The CustomerDeletedEvent to publish.
     */
    @Override
    public void publish(CustomerDeletedEvent customerDeletedEvent) {
        log.info("Received CustomerDeletedEvent for customer id: {}",
                customerDeletedEvent.getCustomer().getId().getValue());
        try {
            CustomerAvroModel customerAvroModel = customerMessagingDataMapper
                    .customerDeletedEventToCustomerAvroModel(customerDeletedEvent);

            kafkaProducer.send(customerServiceConfigData.getCustomerDeletedTopicName(), customerAvroModel.getId(),
                    customerAvroModel,
                    getCallback(customerServiceConfigData.getCustomerDeletedTopicName(), customerAvroModel));

            log.info("CustomerCreatedEvent sent to kafka for customer id: {}",
                    customerAvroModel.getId());
        } catch (Exception e) {
            log.error("Error while sending CustomerDeletedEvent to kafka for customer id: {}," +
                    " error: {}", customerDeletedEvent.getCustomer().getId().getValue(), e.getMessage());
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
