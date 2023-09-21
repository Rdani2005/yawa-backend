package com.rdani2005.yawa.accounts.service.domain.exception;

import com.rdani2005.yawa.domain.exception.DomainException;

/**
 * Custom exception class for domain-specific exceptions related to the 'Accounts' domain.
 * Extends the DomainException class.
 */
public class AccountsDomainException extends DomainException {
    /**
     * Constructs a new AccountsDomainException with no detail message.
     */
    public AccountsDomainException() {
    }

    /**
     * Constructs a new AccountsDomainException with the specified detail message.
     *
     * @param message The detail message.
     */
    public AccountsDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountsDomainException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public AccountsDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AccountsDomainException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public AccountsDomainException(Throwable cause) {
        super(cause);
    }
}

