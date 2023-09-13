package com.rdani2005.yawa.accounts.service.domain.outbox.scheduler.transaction;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction.TransactionOutboxMessage;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.transaction.TransactionResponseMessagePublisher;
import com.rdani2005.yawa.outbox.OutboxScheduler;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The TransactionOutboxScheduler class is responsible for processing and scheduling transaction outbox messages.
 * It retrieves pending outbox messages and sends them to their respective destinations using the provided
 * TransactionResponseMessagePublisher.
 */
@Slf4j
@Component
public class TransactionOutboxScheduler implements OutboxScheduler {
    private final TransactionResponseMessagePublisher transactionResponseMessagePublisher;
    private final TransactionOutboxHelper transactionOutboxHelper;

    /**
     * Constructor for the TransactionOutboxScheduler class.
     *
     * @param transactionResponseMessagePublisher The publisher for transaction response messages.
     * @param transactionOutboxHelper             The helper class for managing transaction outbox messages.
     */
    public TransactionOutboxScheduler(
            TransactionResponseMessagePublisher transactionResponseMessagePublisher,
            TransactionOutboxHelper transactionOutboxHelper
    ) {
        this.transactionResponseMessagePublisher = transactionResponseMessagePublisher;
        this.transactionOutboxHelper = transactionOutboxHelper;
    }

    /**
     * Process and send the outbox messages.
     * This method is responsible for retrieving pending outbox messages
     * and sending them to their respective destinations.
     */
    @Override
    @Scheduled(
            fixedDelayString = "${accounts-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${accounts-service.outbox-scheduler-initial-delay}"
    )
    public void processOutboxMessage() {
        Optional<List<TransactionOutboxMessage>> outboxMessagesResponse =
                transactionOutboxHelper.getTransactionOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.STARTED,
                        SagaStatus.PROCESSING
                );
        if (outboxMessagesResponse.isPresent() && !outboxMessagesResponse.get().isEmpty()) {
            List<TransactionOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            publishOutboxMessages(outboxMessages);
        }
    }

    private void publishOutboxMessages(List<TransactionOutboxMessage> outboxMessages) {
        log.info(
                "Received {} TransactionOutboxMessage wid ids: {}, sending to message bus!",
                outboxMessages.size(),
                outboxMessages
                        .stream()
                        .map(outboxMessage -> outboxMessage.getId().toString())
                        .collect(Collectors.joining(","))
        );
        outboxMessages.forEach(
                outboxMessage -> transactionResponseMessagePublisher.publish(
                        outboxMessage,
                        this::updateOutboxStatus
                )
        );
        log.info("{} TransactionOutboxMessage sent to message bus!", outboxMessages.size());
    }

    private void updateOutboxStatus(
            TransactionOutboxMessage transactionOutboxMessage,
            OutboxStatus outboxStatus
    ) {
        transactionOutboxMessage.setOutboxStatus(outboxStatus);
        transactionOutboxHelper.save(transactionOutboxMessage);
        log.info("TransactionOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
