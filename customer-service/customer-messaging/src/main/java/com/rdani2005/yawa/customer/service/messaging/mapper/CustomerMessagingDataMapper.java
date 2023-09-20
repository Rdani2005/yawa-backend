package com.rdani2005.yawa.customer.service.messaging.mapper;

import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;
import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;
import com.rdani2005.yawa.kafka.avro.model.CustomerAvroModel;
import org.springframework.stereotype.Component;

/**
 * This component is responsible for mapping customer-related events to Avro models
 * for Kafka messaging purposes.
 */
@Component
public class CustomerMessagingDataMapper {

    /**
     * Maps a CustomerCreatedEvent to a CustomerAvroModel.
     *
     * @param customerCreatedEvent The CustomerCreatedEvent to be mapped.
     * @return A CustomerAvroModel representing the customer.
     */
    public CustomerAvroModel customerCreatedEventToCustomerAvroModel(
            CustomerCreatedEvent customerCreatedEvent
    ) {
        return CustomerAvroModel
                .newBuilder()
                .setId(customerCreatedEvent.getCustomer().getId().getValue().toString())
                .setName(customerCreatedEvent.getCustomer().getName())
                .setLastName(customerCreatedEvent.getCustomer().getLastName())
                .setIdentification(customerCreatedEvent.getCustomer().getIdentification())
                .setBirthDay(customerCreatedEvent.getCustomer().getBirthDay().toInstant())
                .setCreatedAt(customerCreatedEvent.getCustomer().getCreatedAt().toInstant())
                .build();
    }

    /**
     * Maps a CustomerDeletedEvent to a CustomerAvroModel.
     *
     * @param customerDeletedEvent The CustomerDeletedEvent to be mapped.
     * @return A CustomerAvroModel representing the deleted customer.
     */
    public CustomerAvroModel customerDeletedEventToCustomerAvroModel(
            CustomerDeletedEvent customerDeletedEvent
    ) {
        return CustomerAvroModel
                .newBuilder()
                .setId(customerDeletedEvent.getCustomer().getId().getValue().toString())
                .setName(customerDeletedEvent.getCustomer().getName())
                .setLastName(customerDeletedEvent.getCustomer().getLastName())
                .setIdentification(customerDeletedEvent.getCustomer().getIdentification())
                .setBirthDay(customerDeletedEvent.getCustomer().getBirthDay().toInstant())
                .setCreatedAt(customerDeletedEvent.getCustomer().getCreatedAt().toInstant())
                .build();
    }
}
