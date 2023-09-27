package com.rdani2005.yawa.transactions.service.domain;

import com.rdani2005.yawa.transactions.service.domain.entities.Transaction;
import com.rdani2005.yawa.transactions.service.domain.events.TransactionCreatedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.rdani2005.yawa.transactions.service.domain.entities.Transaction.UTC;

public class TransactionDomainServiceImpl implements TransactionsDomainService {
    @Override
    public TransactionCreatedEvent createAndValidateTransaction(Transaction transaction) {
        transaction.initializeTransaction();
        return new TransactionCreatedEvent(transaction, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveTransaction(Transaction transaction) {
        transaction.acceptTransaction();
    }
}
