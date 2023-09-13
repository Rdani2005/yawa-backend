package com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit.DepositOutboxMessage;
import com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction.TransactionOutboxMessage;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionOutboxRepository {
    /**
     * Method that saves a new {@code DepositOutboxMessage} object
     * @param transactionOutboxRepository contains the information that we are saving
     * @return the new {@code OrderPaymentOutboxMessage} object.
     */
    TransactionOutboxMessage save(TransactionOutboxMessage transactionOutboxRepository);

    /**
     * Gets a list of  {@code TransactionOutboxRepository} objects, filtered by some parameters
     * @param type type of messages that we are looking
     * @param outboxStatus outbox processed status
     * @param sagaStatus saga processed status
     * @return list of  {@code OrderPaymentOutboxMessage} objects
     */
    Optional<List<TransactionOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );

    /**
     * Gets a single {@code TransactionOutboxRepository} object, with some filters
     * @param type type of message that we are looking
     * @param sagaId saga unique identifier
     * @param sagaStatus actual saga status
     * @return {@code OrderPaymentOutboxMessage} object filtered.
     */
    Optional<TransactionOutboxMessage> findByTypeAndSagaIdAndSagaStatus(
            String type,
            UUID sagaId,
            SagaStatus... sagaStatus
    );

    /**
     * Deletes a list of {@code TransactionOutboxRepository} objects with filters
     * @param type type of messages to delete
     * @param outboxStatus outbox status to delete
     * @param sagaStatus saga status to delete
     */
    void deleteByTypeAndOutboxStatusAndSagaStatus(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );
}
