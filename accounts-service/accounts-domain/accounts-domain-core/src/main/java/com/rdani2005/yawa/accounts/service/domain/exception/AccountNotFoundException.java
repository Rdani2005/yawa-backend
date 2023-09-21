package com.rdani2005.yawa.accounts.service.domain.exception;

public class AccountNotFoundException extends AccountsDomainException {
    /**
     * Constructs a new AccountsDomainException with no detail message.
     */
    public AccountNotFoundException() {
    }

    /**
     * Constructs a new AccountsDomainException with the specified detail message.
     *
     * @param message The detail message.
     */
    public AccountNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountsDomainException with the specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AccountsDomainException with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and detail message of cause).
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
