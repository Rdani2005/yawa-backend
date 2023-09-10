package com.rdani2005.yawa.accounts.service.domain.exception;

import com.rdani2005.yawa.domain.exception.DomainException;

/**
 * Represents an exception specific to the domain of account operations.
 */
public class AccountDomainException extends DomainException {
    /**
     * Constructs a new AccountDomainException with no detail message.
     */
    public AccountDomainException() {
    }

    /**
     * Constructs a new AccountDomainException with the specified detail message.
     *
     * @param message The detail message explaining the exception.
     */
    public AccountDomainException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountDomainException with the specified detail message and cause.
     *
     * @param message The detail message explaining the exception.
     * @param cause   The cause of the exception.
     */
    public AccountDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AccountDomainException with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public AccountDomainException(Throwable cause) {
        super(cause);
    }
}
