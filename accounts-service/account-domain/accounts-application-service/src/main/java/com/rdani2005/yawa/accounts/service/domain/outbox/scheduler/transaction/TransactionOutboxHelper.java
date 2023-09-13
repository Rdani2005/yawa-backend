package com.rdani2005.yawa.accounts.service.domain.outbox.scheduler.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction.TransactionOutboxMessage;
import com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction.TransactionPayload;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.TransactionOutboxRepository;
import com.rdani2005.yawa.domain.valueobject.TransactionStatus;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rdani2005.yawa.saga.account.SagaConstants.TRANSACTION_SAGA_NAME;

/**
 * Helper class for managing transaction outbox messages.
 * This class provides methods for retrieving, saving, and deleting transaction outbox messages.
 */
@Slf4j
@Component
public class TransactionOutboxHelper {
    private final TransactionOutboxRepository transactionOutboxRepository;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new TransactionOutboxHelper with the given dependencies.
     *
     * @param transactionOutboxRepository The repository for transaction outbox messages.
     * @param objectMapper                The ObjectMapper for JSON serialization.
     */
    public TransactionOutboxHelper(
            TransactionOutboxRepository transactionOutboxRepository,
            ObjectMapper objectMapper
    ) {
        this.transactionOutboxRepository = transactionOutboxRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieve transaction outbox messages based on outbox status and saga status.
     *
     * @param outboxStatus The outbox status to filter by.
     * @param sagaStatus   The saga status to filter by (varargs).
     * @return A list of transaction outbox messages matching the specified criteria.
     */
    @Transactional(readOnly = true)
    public Optional<List<TransactionOutboxMessage>> getTransactionOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    )  {
        return transactionOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(
                TRANSACTION_SAGA_NAME,
                outboxStatus,
                sagaStatus
        );
    }

    /**
     * Retrieve a transaction outbox message by saga ID and saga status.
     *
     * @param sagaId     The saga ID to filter by.
     * @param sagaStatus The saga status to filter by (varargs).
     * @return The transaction outbox message matching the specified criteria.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionOutboxMessage> getTransactionOutboxMessageBySagaIdAndSagaStatus(
            UUID sagaId,
            SagaStatus... sagaStatus
    ) {
        return transactionOutboxRepository.findByTypeAndSagaIdAndSagaStatus(
                TRANSACTION_SAGA_NAME,
                sagaId,
                sagaStatus
        );
    }

    /**
     * Save a transaction outbox message.
     *
     * @param transactionOutboxMessage The transaction outbox message to save.
     */
    @Transactional
    public void save(TransactionOutboxMessage transactionOutboxMessage) {
        TransactionOutboxMessage response = transactionOutboxRepository.save(transactionOutboxMessage);
        if (response == null) {
            log.error(
                    "Could not save TransactionOutboxMessage with outbox id: {}",
                    transactionOutboxMessage.getId()
            );
            throw new AccountDomainException(
                    "Could not save TransactionOutboxMessage with outbox id: "
                            + transactionOutboxMessage.getId()
            );
        }

        log.info("DepositOutboxMessage saved with outbox id: {}", transactionOutboxMessage.getId());
    }

    /**
     * Delete transaction outbox messages based on outbox status and saga status.
     *
     * @param outboxStatus The outbox status to filter by.
     * @param sagaStatus   The saga status to filter by (varargs).
     */
    @Transactional
    public void deleteTransactionOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    ) {
        transactionOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(
                TRANSACTION_SAGA_NAME,
                outboxStatus,
                sagaStatus
        );
    }

    /**
     * Save a transaction outbox message with payload and other details.
     *
     * @param transactionPayload    The payload of the transaction.
     * @param transactionStatus     The transaction status.
     * @param sagaStatus            The saga status.
     * @param outboxStatus          The outbox status.
     * @param sagaId                The saga ID.
     */
    @Transactional
    public void saveOutboxMessage(
            TransactionPayload transactionPayload,
            TransactionStatus transactionStatus,
            SagaStatus sagaStatus,
            OutboxStatus outboxStatus,
            UUID sagaId
    ) {
        save(
               TransactionOutboxMessage
                       .builder()
                       .id(UUID.randomUUID())
                       .sagaId(sagaId)
                       .createdAt(transactionPayload.getCreatedAt())
                       .type(TRANSACTION_SAGA_NAME)
                       .payload(createPayload(transactionPayload))
                       .outboxStatus(outboxStatus)
                       .sagaStatus(sagaStatus)
                       .transactionStatus(transactionStatus)
                       .build()
        );
    }

    private String createPayload(TransactionPayload transactionPayload) {
        try {
            return objectMapper.writeValueAsString(transactionPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderPaymentEventPayload objects for order id: {}",
                    transactionPayload.getAccountId(), e);
            throw new AccountDomainException(
                    "Could not create OrderPaymentEventPayload objects for order id: "
                            + transactionPayload.getAccountId()
            );
        }
    }
}
