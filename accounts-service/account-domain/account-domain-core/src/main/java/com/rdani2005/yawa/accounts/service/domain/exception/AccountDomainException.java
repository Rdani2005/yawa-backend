package com.rdani2005.yawa.accounts.service.domain.exception;

import com.rdani2005.yawa.domain.exception.DomainException;

public class AccountDomainException extends DomainException {
    public AccountDomainException() {
    }

    public AccountDomainException(String message) {
        super(message);
    }

    public AccountDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountDomainException(Throwable cause) {
        super(cause);
    }
}
