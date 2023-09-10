package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;

import java.time.ZonedDateTime;

/**
 * Represents an event indicating that a movement (transaction) on an account has been denied.
 */
public class AccountMovementDeniedEvent extends AccountEvent {
    /**
     * Constructs an instance of AccountMovementDeniedEvent.
     *
     * @param account   The account for which the movement was denied.
     * @param createdAt The timestamp when the movement denial event was created.
     */
    public AccountMovementDeniedEvent(Account account, ZonedDateTime createdAt) {
        super(account, createdAt);
    }
}
