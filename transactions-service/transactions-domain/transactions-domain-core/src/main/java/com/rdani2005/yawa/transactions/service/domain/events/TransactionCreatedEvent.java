package com.rdani2005.yawa.transactions.service.domain.events;

import com.rdani2005.yawa.transactions.service.domain.entities.Transaction;

import java.time.ZonedDateTime;

public class TransactionCreatedEvent extends TransactionEvent {
    public TransactionCreatedEvent(
            Transaction transaction,
            ZonedDateTime createdAt
    ) {
        super(transaction, createdAt);
    }
}
