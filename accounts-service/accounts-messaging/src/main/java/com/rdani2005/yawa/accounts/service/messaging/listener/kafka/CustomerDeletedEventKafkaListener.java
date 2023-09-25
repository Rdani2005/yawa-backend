package com.rdani2005.yawa.accounts.service.messaging.listener.kafka;

import com.rdani2005.yawa.accounts.service.domain.ports.input.listener.CustomerDeletedEventListener;
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

@Slf4j
@Component
public class CustomerDeletedEventKafkaListener implements KafkaConsumer<CustomerAvroModel> {
    /**
     * Kafka listener for processing customer deletion events.
     */
    private final CustomerDeletedEventListener customerDeletedEventListener;
    private final AccountsMessagingDataMapper accountsMessagingDataMapper;

    /**
     * Constructs an instance of CustomerDeletedEventKafkaListener.
     *
     * @param customerDeletedEventListener   The listener for handling customer deletion events.
     * @param accountsMessagingDataMapper    The data mapper for mapping between Avro models and domain models.
     */
    public CustomerDeletedEventKafkaListener(
            CustomerDeletedEventListener customerDeletedEventListener,
            AccountsMessagingDataMapper accountsMessagingDataMapper
    ) {
        this.customerDeletedEventListener = customerDeletedEventListener;
        this.accountsMessagingDataMapper = accountsMessagingDataMapper;
    }

    /**
     * @inheritDoc
     */
    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.customer-deleted-group-id}",
            topics = "${accounts-service.customer-deleted-topic-name}"
    )
    public void receive(
            @Payload List<CustomerAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        log.info("{} number of customer delete messages received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString()
        );

        messages.forEach(customerAvroModel ->
                customerDeletedEventListener.deleteCustomer(
                        accountsMessagingDataMapper.customerAvroModeltoCustomerModel(customerAvroModel)
                )
        );
    }
}
