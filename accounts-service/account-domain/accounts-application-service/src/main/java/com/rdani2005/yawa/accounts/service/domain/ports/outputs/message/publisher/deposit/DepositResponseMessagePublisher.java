package com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.deposit;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.deposit.DepositOutboxMessage;
import com.rdani2005.yawa.outbox.OutboxStatus;

import java.util.function.BiConsumer;

/**
 * An interface for publishing deposit response messages.
 */
public interface DepositResponseMessagePublisher {
    /**
     * Publishes a deposit response message and provides a callback function for handling the outbox status.
     *
     * @param depositOutboxMessage The deposit outbox message to be published.
     * @param outboxCallback       A callback function that takes the deposit outbox message and outbox status as parameters.
     */
    void publish(
            DepositOutboxMessage depositOutboxMessage,
            BiConsumer<DepositOutboxMessage, OutboxStatus> outboxCallback
    );
}
