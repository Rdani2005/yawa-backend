package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.domain.events.DomainEvent;

import java.time.ZonedDateTime;

/**
 * Represents an abstract base class for events related to account operations.
 */
public abstract class AccountEvent implements DomainEvent<AccountEvent> {
    private final Account account;
    private final ZonedDateTime createdAt;
    /**
     * Initializes an AccountEvent with the associated account and creation timestamp.
     *
     * @param account    The account related to this event.
     * @param createdAt  The timestamp when the event was created.
     */
    public AccountEvent(
            Account account,
            ZonedDateTime createdAt
    ) {
        this.account = account;
        this.createdAt = createdAt;
    }

    /**
     * Gets the account associated with this event.
     *
     * @return The account related to this event.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Gets the creation timestamp of this event.
     *
     * @return The timestamp when the event was created.
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
