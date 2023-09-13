package com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.transaction;

import com.rdani2005.yawa.accounts.service.domain.outbox.model.transaction.TransactionOutboxMessage;
import com.rdani2005.yawa.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface TransactionResponseMessagePublisher {
    void publish(
            TransactionOutboxMessage depositOutboxMessage,
            BiConsumer<TransactionOutboxMessage, OutboxStatus> outboxCallback
    );
}
