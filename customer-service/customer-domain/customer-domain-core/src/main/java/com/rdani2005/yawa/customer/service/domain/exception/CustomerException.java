package com.rdani2005.yawa.customer.service.domain.exception;

import com.rdani2005.yawa.domain.exception.DomainException;

public class CustomerException extends DomainException {
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }

    public CustomerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerException(Throwable cause) {
        super(cause);
    }
}
