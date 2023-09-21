package com.rdani2005.yawa.accounts.service.domain.event;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.domain.events.DomainEvent;

import java.time.ZonedDateTime;

/**
 * An abstract class representing an event related to an Account.
 */
public abstract class AccountsEvent implements DomainEvent<Account> {
    private final Account account;
    private final ZonedDateTime createdAt;

    /**
     * Constructs a new AccountsEvent with the specified account and creation timestamp.
     *
     * @param account   The Account related to the event.
     * @param createdAt The timestamp when the event was created.
     */
    public AccountsEvent(
            Account account,
            ZonedDateTime createdAt
    ) {
        this.account = account;
        this.createdAt = createdAt;
    }

    /**
     * Gets the Account related to the event.
     *
     * @return The related Account.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Gets the timestamp when the event was created.
     *
     * @return The creation timestamp.
     */
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
