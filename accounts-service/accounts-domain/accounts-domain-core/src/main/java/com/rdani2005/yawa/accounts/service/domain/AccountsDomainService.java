package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsDeletedEvent;

/**
 * Interface representing the domain service for managing accounts.
 */
public interface AccountsDomainService {
    /**
     * Create a new account and publish an event.
     *
     * @param account The account to be created.
     * @return An event indicating that the account was created.
     */
    AccountsCreatedEvent createAccount(Account account);

    /**
     * Delete an existing account and publish an event.
     *
     * @param account The account to be deleted.
     * @return An event indicating that the account was deleted.
     */
    AccountsDeletedEvent deleteAccount(Account account);
}
