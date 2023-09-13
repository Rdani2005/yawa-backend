package com.rdani2005.yawa.accounts.service.domain.outbox.scheduler.transaction;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction.TransactionOutboxMessage;
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
 * Scheduled component responsible for cleaning up completed transaction outbox messages.
 * It retrieves transaction outbox messages with specific statuses and deletes them.
 */
@Slf4j
@Component
public class TransactionOutboxCleaner implements OutboxScheduler {
    private final TransactionOutboxHelper transactionOutboxHelper;

    /**
     * Constructor for the TransactionOutboxCleaner class.
     *
     * @param transactionOutboxHelper The helper class for managing transaction outbox messages.
     */
    public TransactionOutboxCleaner(TransactionOutboxHelper transactionOutboxHelper) {
        this.transactionOutboxHelper = transactionOutboxHelper;
    }

    /**
     * Process and clean up completed transaction outbox messages.
     * This method is scheduled to run at a specified interval to retrieve transaction
     * outbox messages with specific statuses (COMPLETED) and delete them.
     */
    @Override
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<TransactionOutboxMessage>> outboxMessagesResponse = transactionOutboxHelper
                .getTransactionOutboxMessageByOutboxStatusAndSagaStatus(
                        OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED
                );

        if (outboxMessagesResponse.isPresent()) {
            List<TransactionOutboxMessage> outboxMessages = outboxMessagesResponse.get();
            deleteMessages(outboxMessages);
        }
    }

    /**
     * Delete the specified transaction outbox messages.
     *
     * @param outboxMessages The list of transaction outbox messages to be deleted.
     */
    private void deleteMessages(List<TransactionOutboxMessage> outboxMessages) {
        log.info(
                "Received {} TransactionOutboxMessages for clean-up. Payloads: {}",
                outboxMessages.size(),
                outboxMessages
                        .stream()
                        .map(TransactionOutboxMessage::getPayload)
                        .collect(Collectors.joining("\n"))
        );

        transactionOutboxHelper.deleteTransactionOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
        );

        log.info("{} TransactionOutboxMessages deleted!", outboxMessages.size());
    }
}
