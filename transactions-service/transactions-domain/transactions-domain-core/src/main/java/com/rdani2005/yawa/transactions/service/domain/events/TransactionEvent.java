package com.rdani2005.yawa.transactions.service.domain.events;

import com.rdani2005.yawa.domain.events.DomainEvent;
import com.rdani2005.yawa.transactions.service.domain.entities.Transaction;

import java.time.ZonedDateTime;

public class TransactionEvent implements DomainEvent<Transaction> {
    private final Transaction transaction;
    private final ZonedDateTime createdAt;

    public TransactionEvent(
            Transaction transaction,
            ZonedDateTime createdAt
    ) {
        this.transaction = transaction;
        this.createdAt = createdAt;
    }
}
