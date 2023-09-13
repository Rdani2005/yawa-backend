package com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit;

import com.rdani2005.yawa.domain.valueobject.DepositStatus;
import com.rdani2005.yawa.outbox.OutboxStatus;
import com.rdani2005.yawa.saga.SagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Represents an outbox message for deposit-related operations.
 * This class encapsulates information about a deposit-related operation, including its status, payload,
 * and relevant timestamps.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DepositOutboxMessage {
    private UUID id; // The unique identifier for the outbox message
    private UUID sagaId; // The identifier of the associated saga
    private ZonedDateTime createdAt; // The timestamp when the message was created
    private ZonedDateTime processedAt; // The timestamp when the message was processed
    private String type; // The type of the outbox message
    private String payload; // The payload of the message
    private SagaStatus sagaStatus; // The status of the associated saga
    private DepositStatus depositStatus; // The status of the deposit operation
    private OutboxStatus outboxStatus; // The status of the outbox message
    private int version; // The version of the outbox message
}






