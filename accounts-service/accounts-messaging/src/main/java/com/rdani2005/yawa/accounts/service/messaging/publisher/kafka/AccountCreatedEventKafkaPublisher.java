package com.rdani2005.yawa.accounts.service.messaging.publisher.kafka;

import com.rdani2005.yawa.accounts.service.domain.config.AccountsServiceConfigData;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.ports.output.message.publisher.AccountCreatedEventPublisher;
import com.rdani2005.yawa.accounts.service.messaging.mapper.AccountsMessagingDataMapper;
import com.rdani2005.yawa.kafka.avro.model.AccountAvroModel;
import com.rdani2005.yawa.kafka.producer.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Implementation of the {@link AccountCreatedEventPublisher} interface that publishes
 * {@link AccountsCreatedEvent} to a Kafka topic.
 *
 */
@Slf4j
@Component
public class AccountCreatedEventKafkaPublisher implements AccountCreatedEventPublisher {

    private final AccountsMessagingDataMapper accountsMessagingDataMapper;
    private final KafkaProducer<String, AccountAvroModel> kafkaProducer;
    private final AccountsServiceConfigData accountsServiceConfigData;

    /**
     * Constructor to initialize the Kafka publisher.
     *
     * @param accountsMessagingDataMapper    The data mapper for mapping account data.
     * @param kafkaProducer                  The Kafka producer for publishing messages.
     * @param accountsServiceConfigData      The configuration data for the accounts service.
     */
    public AccountCreatedEventKafkaPublisher(
            AccountsMessagingDataMapper accountsMessagingDataMapper,
            KafkaProducer<String, AccountAvroModel> kafkaProducer,
            AccountsServiceConfigData accountsServiceConfigData
    ) {
        this.accountsMessagingDataMapper = accountsMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.accountsServiceConfigData = accountsServiceConfigData;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void publish(AccountsCreatedEvent accountsCreatedEvent) {
        log.info("Received a AccountCreatedEvent for account id: {}", accountsCreatedEvent.getAccount().getId().getValue());
        try {
            AccountAvroModel accountAvroModel =
                    accountsMessagingDataMapper.accountToAccountAvroModel(accountsCreatedEvent.getAccount());

            kafkaProducer.send(accountsServiceConfigData.getAccountCreatedTopicName(), accountAvroModel.getId(),
                    accountAvroModel,
                    getCallback(accountsServiceConfigData.getAccountCreatedTopicName(), accountAvroModel));

            log.info("AccountsCreatedEvent sent to kafka for customer id: {}",
                    accountAvroModel.getId());

        } catch (Exception e) {
            log.error("Error while sending CustomerCreatedEvent to kafka for customer id: {}," +
                    " error: {}", accountsCreatedEvent.getAccount().getId().getValue(), e.getMessage());
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
