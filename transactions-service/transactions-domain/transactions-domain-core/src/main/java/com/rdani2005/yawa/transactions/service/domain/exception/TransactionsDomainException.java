package com.rdani2005.yawa.transactions.service.domain.exception;

import com.rdani2005.yawa.domain.exception.DomainException;

public class TransactionsDomainException extends DomainException {
    public TransactionsDomainException() {
    }

    public TransactionsDomainException(String message) {
        super(message);
    }

    public TransactionsDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionsDomainException(Throwable cause) {
        super(cause);
    }
}
