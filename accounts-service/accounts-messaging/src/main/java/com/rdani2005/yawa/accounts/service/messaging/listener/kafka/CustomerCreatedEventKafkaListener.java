package com.rdani2005.yawa.accounts.service.messaging.listener.kafka;

import com.rdani2005.yawa.accounts.service.domain.ports.input.listener.CustomerCreatedEventListener;
import com.rdani2005.yawa.accounts.service.messaging.mapper.AccountsMessagingDataMapper;
import com.rdani2005.yawa.consumer.KafkaConsumer;
import com.rdani2005.yawa.kafka.avro.model.CustomerAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kafka listener for processing customer creation events.
 */
@Slf4j
@Component
public class CustomerCreatedEventKafkaListener implements KafkaConsumer<CustomerAvroModel> {

    private final CustomerCreatedEventListener customerCreatedEventListener;
    private final AccountsMessagingDataMapper accountsMessagingDataMapper;

    /**
     * Constructs an instance of CustomerCreatedEventKafkaListener.
     *
     * @param customerCreatedEventListener   The listener for handling customer creation events.
     * @param accountsMessagingDataMapper    The data mapper for mapping between Avro models and domain models.
     */
    public CustomerCreatedEventKafkaListener(
            CustomerCreatedEventListener customerCreatedEventListener,
            AccountsMessagingDataMapper accountsMessagingDataMapper
    ) {
        this.customerCreatedEventListener = customerCreatedEventListener;
        this.accountsMessagingDataMapper = accountsMessagingDataMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.customer-created-group-id}",
            topics = "${accounts-service.customer-created-topic-name}"
    )
    public void receive(
            @Payload List<CustomerAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        log.info("{} number of customer create messages received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString()
        );

        messages.forEach(customerAvroModel ->
                customerCreatedEventListener.CustomerCreated(
                        accountsMessagingDataMapper.customerAvroModeltoCustomerModel(customerAvroModel)
                )
        );
    }
}
