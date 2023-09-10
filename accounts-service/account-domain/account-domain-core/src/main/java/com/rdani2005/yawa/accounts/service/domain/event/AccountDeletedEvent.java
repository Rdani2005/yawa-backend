package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;

import java.time.ZonedDateTime;

/**
 * Represents an event that indicates the deletion of an account.
 */
public class AccountDeletedEvent extends AccountEvent {
    /**
     * Initializes an AccountDeletedEvent with the associated account and deletion timestamp.
     *
     * @param account    The account that was deleted.
     * @param createdAt  The timestamp when the account was deleted.
     */
    public AccountDeletedEvent(
            Account account,
            ZonedDateTime createdAt
    ) {
        super(account, createdAt);
    }
}
