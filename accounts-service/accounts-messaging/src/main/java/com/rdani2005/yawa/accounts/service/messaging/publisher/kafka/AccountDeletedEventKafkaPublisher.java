package com.rdani2005.yawa.accounts.service.messaging.publisher.kafka;

import com.rdani2005.yawa.accounts.service.domain.config.AccountsServiceConfigData;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsDeletedEvent;
import com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher.AccountDeletedEventPublisher;
import com.rdani2005.yawa.accounts.service.messaging.mapper.AccountsMessagingDataMapper;
import com.rdani2005.yawa.kafka.avro.model.AccountAvroModel;
import com.rdani2005.yawa.kafka.producer.service.KafkaProducer;

import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.RecordMetadata;

import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * A Kafka publisher for sending account deletion events to a Kafka topic.
 */
@Slf4j
@Component
public class AccountDeletedEventKafkaPublisher implements AccountDeletedEventPublisher {
    private final AccountsMessagingDataMapper accountsMessagingDataMapper;
    private final KafkaProducer<String, AccountAvroModel> kafkaProducer;
    private final AccountsServiceConfigData accountsServiceConfigData;

    /**
     * Constructs an instance of AccountDeletedEventKafkaPublisher.
     *
     * @param accountsMessagingDataMapper     The data mapper for mapping between domain and Avro models.
     * @param kafkaProducer                   The Kafka producer for sending messages.
     * @param accountsServiceConfigData       The configuration data for the accounts service.
     */
    public AccountDeletedEventKafkaPublisher(
            AccountsMessagingDataMapper accountsMessagingDataMapper,
            KafkaProducer<String, AccountAvroModel> kafkaProducer,
            AccountsServiceConfigData accountsServiceConfigData
    ) {
        this.accountsMessagingDataMapper = accountsMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.accountsServiceConfigData = accountsServiceConfigData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(AccountsDeletedEvent accountsDeletedEvent) {
        log.info("Received AccountsDeletedEvent for customer id: {}",
                accountsDeletedEvent.getAccount().getId().getValue());
        try {
            AccountAvroModel accountAvroModel = accountsMessagingDataMapper
                    .accountToAccountAvroModel(accountsDeletedEvent.getAccount());

            kafkaProducer.send(accountsServiceConfigData.getAccountDeletedTopicName(), accountAvroModel.getId(),
                    accountAvroModel,
                    getCallback(accountsServiceConfigData.getAccountDeletedTopicName(), accountAvroModel));

            log.info("AccountsDeletedEvent sent to kafka for account id: {}",
                    accountAvroModel.getId());
        } catch (Exception e) {
            log.error("Error while sending AccountsDeletedEvent to kafka for account id: {}," +
                    " error: {}", accountsDeletedEvent.getAccount().getId().getValue(), e.getMessage());
        }
    }

    private ListenableFutureCallback<SendResult<String, AccountAvroModel>>
    getCallback(String topicName, AccountAvroModel message) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error while sending message {} to topic {}", message.toString(), topicName, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, AccountAvroModel> result) {
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
