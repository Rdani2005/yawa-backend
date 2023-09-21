package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;

import java.time.ZonedDateTime;

/**
 * A class representing an account created event.
 */
public class AccountsCreatedEvent extends AccountsEvent {
    /**
     * Constructs a new AccountsCreatedEvent with the specified account and creation timestamp.
     *
     * @param account   The Account related to the event.
     * @param createdAt The timestamp when the event was created.
     */
    public AccountsCreatedEvent(
            Account account,
            ZonedDateTime createdAt
    ) {
        super(account, createdAt);
    }
}
