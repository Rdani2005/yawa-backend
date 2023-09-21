package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsDeletedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.rdani2005.yawa.accounts.service.domain.entity.Account.UTC;

/**
 * Implementation of the {@link AccountsDomainService} interface.
 */
public class AccountsDomainServiceImpl implements AccountsDomainService{

    /**
     * Create a new account and publish an event.
     *
     * @param account The account to be created.
     * @return An event indicating that the account was created.
     */
    @Override
    public AccountsCreatedEvent createAccount(Account account) {
        account.initializeAccount();
        return new AccountsCreatedEvent(
                account,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }

    /**
     * Delete an existing account and publish an event.
     *
     * @param account The account to be deleted.
     * @return An event indicating that the account was deleted.
     */
    @Override
    public AccountsDeletedEvent deleteAccount(Account account) {
        account.verifyAccountDeletedEvent();
        return new AccountsDeletedEvent(
                account,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }
}
