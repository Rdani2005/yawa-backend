package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;

import java.time.ZonedDateTime;

/**
 * Represents an event indicating a deposit made to an account.
 */
public class AccountDepositEvent extends AccountEvent {
    /**
     * Constructs an instance of AccountDepositEvent.
     *
     * @param account   The account to which the deposit was made.
     * @param createdAt The timestamp when the deposit event was created.
     */
    public AccountDepositEvent(
            Account account,
            ZonedDateTime createdAt
    ) {
        super(account, createdAt);
    }
}
