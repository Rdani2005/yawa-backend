package com.rdani2005.yawa.transactions.service.domain;

import com.rdani2005.yawa.transactions.service.domain.entities.Transaction;
import com.rdani2005.yawa.transactions.service.domain.events.TransactionCreatedEvent;

public interface TransactionsDomainService {
    TransactionCreatedEvent createAndValidateTransaction(Transaction transaction);
    void approveTransaction(Transaction transaction);
}
