package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;

import java.time.ZonedDateTime;
/**
 * Represents an event that indicates the creation of a new account.
 */
public class AccountCreatedEvent extends AccountEvent {
    /**
     * Initializes an AccountCreatedEvent with the associated account and creation timestamp.
     *
     * @param account    The account that was created.
     * @param createdAt  The timestamp when the account was created.
     */
    public AccountCreatedEvent(
            Account account,
            ZonedDateTime createdAt
    ) {
        super(account, createdAt);
    }
}
