package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;

import java.time.ZonedDateTime;

/**
 * Represents an event that indicates the approval of a movement (transaction) on an account.
 */
public class AccountMovementApproved extends AccountEvent {

    /**
     * Initializes an AccountMovementApproved event with the associated account and approval timestamp.
     *
     * @param account    The account for which the movement was approved.
     * @param createdAt  The timestamp when the movement was approved.
     */
    public AccountMovementApproved(
            Account account,
            ZonedDateTime createdAt
    ) {
        super(account, createdAt);
    }
}
