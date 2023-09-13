package com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction;

import com.rdani2005.yawa.domain.valueobject.TransactionStatus;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Represents an outbox message for a transaction, containing relevant information about the transaction.
 * This class encapsulates data such as message identifier, saga identifier, creation and processing timestamps,
 * message type, payload, saga status, transaction status, outbox status, and version.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransactionOutboxMessage {
    private UUID id; // The identifier of the outbox message
    private UUID sagaId; // The identifier of the associated saga
    private ZonedDateTime createdAt; // The timestamp when the message was created
    private ZonedDateTime processedAt; // The timestamp when the message was processed
    private String type; // The type of the message
    private String payload; // The payload of the message
    private SagaStatus sagaStatus; // The status of the associated saga
    private TransactionStatus transactionStatus; // The status of the transaction
    private OutboxStatus outboxStatus; // The status of the outbox message
    private int version; // The version of the outbox message
}