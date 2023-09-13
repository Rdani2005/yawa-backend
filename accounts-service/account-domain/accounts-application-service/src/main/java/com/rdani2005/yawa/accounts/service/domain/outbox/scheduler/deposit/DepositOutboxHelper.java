package com.rdani2005.yawa.accounts.service.domain.outbox.scheduler.deposit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit.DepositPayload;
import com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit.DepositOutboxMessage;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.DepositlOutboxRepository;

import com.rdani2005.yawa.domain.valueobject.DepositStatus;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.rdani2005.yawa.saga.account.SagaConstants.DEPOSIT_SAGA_NAME;

/**
 * Helper class for managing deposit outbox messages.
 */
@Slf4j
@Component
public class DepositOutboxHelper {
    private final DepositlOutboxRepository depositlOutboxRepository;
    private final ObjectMapper objectMapper;

    /**
     * Constructs a new instance of DepositOutboxHelper.
     *
     * @param depositlOutboxRepository The repository for deposit outbox messages.
     * @param objectMapper             The ObjectMapper for JSON serialization.
     */
    public DepositOutboxHelper(
            DepositlOutboxRepository depositlOutboxRepository,
            ObjectMapper objectMapper
    ) {
        this.depositlOutboxRepository = depositlOutboxRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves deposit outbox messages based on outbox status and saga status.
     *
     * @param outboxStatus The outbox status.
     * @param sagaStatus   The saga status (varargs).
     * @return An optional list of deposit outbox messages.
     */
    @Transactional(readOnly = true)
    public Optional<List<DepositOutboxMessage>> getDepositOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    ) {
        return depositlOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(
                DEPOSIT_SAGA_NAME,
                outboxStatus,
                sagaStatus
        );
    }

    /**
     * Retrieves a deposit outbox message based on saga ID and saga status.
     *
     * @param sagaId     The saga ID.
     * @param sagaStatus The saga status (varargs).
     * @return An optional deposit outbox message.
     */
    @Transactional(readOnly = true)
    public Optional<DepositOutboxMessage> getDpositOutboxMessageByBySagaIdAndSagaStatus(
            UUID sagaId,
            SagaStatus... sagaStatus
    ) {
        return depositlOutboxRepository.findByTypeAndSagaIdAndSagaStatus(
                DEPOSIT_SAGA_NAME,
                sagaId,
                sagaStatus
        );
    }

    /**
     * Saves a deposit outbox message.
     *
     * @param depositOutboxMessage The deposit outbox message to save.
     */
    @Transactional
    public void save(DepositOutboxMessage depositOutboxMessage) {
        DepositOutboxMessage response = depositlOutboxRepository.save(depositOutboxMessage);
        if (response == null) {
            log.error("Could not save DepositOutboxMessage with outbox id: {}", depositOutboxMessage.getId());
            throw new AccountDomainException(
                    "Could not save DepositOutboxMessage with outbox id: "
                    + depositOutboxMessage.getId()
            );
        }

        log.info("DepositOutboxMessage saved with outbox id: {}", depositOutboxMessage.getId());
    }

    /**
     * Deletes deposit outbox messages based on outbox status and saga status.
     *
     * @param outboxStatus The outbox status.
     * @param sagaStatus   The saga status (varargs).
     */
    @Transactional
    public void deleteDepositOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    ) {
        depositlOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(
                DEPOSIT_SAGA_NAME,
                outboxStatus,
                sagaStatus
        );
    }

    /**
     * Saves a payment outbox message.
     *
     * @param depositPayload The deposit approval payload.
     * @param depositStatus          The deposit status.
     * @param sagaStatus             The saga status.
     * @param outboxStatus           The outbox status.
     * @param sagaId                 The saga ID.
     */
    @Transactional
    public void savePaymentOutboxMessage(
            DepositPayload depositPayload,
            DepositStatus depositStatus,
            SagaStatus sagaStatus,
            OutboxStatus outboxStatus,
            UUID sagaId
    ) {
        save(
                DepositOutboxMessage
                        .builder()
                        .id(UUID.randomUUID())
                        .sagaId(sagaId)
                        .createdAt(depositPayload.getCreatedAt())
                        .type(DEPOSIT_SAGA_NAME)
                        .payload(createPayload(depositPayload))
                        .outboxStatus(outboxStatus)
                        .sagaStatus(sagaStatus)
                        .depositStatus(depositStatus)
                        .build()
        );
    }

    /**
     * Creates a JSON payload from a deposit approval payload.
     *
     * @param depositPayload The deposit approval payload.
     * @return A JSON string representing the payload.
     */
    private String createPayload(DepositPayload depositPayload) {
        try {
            return objectMapper.writeValueAsString(depositPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderPaymentEventPayload objects for order id: {}",
                    depositPayload.getAccountId(), e);
            throw new AccountDomainException(
                    "Could not create OrderPaymentEventPayload objects for order id: "
                            + depositPayload.getAccountId()
            );
        }
    }
}
