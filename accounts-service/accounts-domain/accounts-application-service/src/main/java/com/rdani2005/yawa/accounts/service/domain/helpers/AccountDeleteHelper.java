package com.rdani2005.yawa.accounts.service.domain.helpers;

import com.rdani2005.yawa.accounts.service.domain.AccountsDomainService;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsDeletedEvent;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountNotFoundException;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.AccountsRepository;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Helper class for deleting user accounts.
 */
@Slf4j
@Component
public class AccountDeleteHelper {
    private final AccountsRepository accountsRepository;
    private final AccountsDomainService accountsDomainService;

    /**
     * Constructs an AccountDeleteHelper with the necessary dependencies.
     *
     * @param accountsRepository    The repository for account data.
     * @param accountsDomainService The service for handling account domain logic.
     */
    public AccountDeleteHelper(
            AccountsRepository accountsRepository,
            AccountsDomainService accountsDomainService
    ) {
        this.accountsRepository = accountsRepository;
        this.accountsDomainService = accountsDomainService;
    }

    /**
     * Deletes an account based on the provided account delete command.
     *
     * @param deleteCommand The command containing account deletion details.
     * @return The accounts deleted event.
     * @throws AccountNotFoundException If the account with the specified ID is not found.
     */
    @Transactional
    public AccountsDeletedEvent deleteAccount(AccountDeleteCommand deleteCommand) {
        Optional<Account> accountResponse = accountsRepository.readAccountByAccountId(
                new AccountId(deleteCommand.getAccountId())
        );

        if (accountResponse.isEmpty()) {
            log.error("Account with id: {} was not found!", deleteCommand.getAccountId());
            throw new AccountNotFoundException("Account with id: " + deleteCommand.getAccountId() + " was not found.");
        }

        AccountsDeletedEvent deletedEvent = accountsDomainService.deleteAccount(accountResponse.get());
        accountsRepository.deleteAccount(accountResponse.get());
        log.info("Account with id: {} was successfully deleted.", deleteCommand.getAccountId());
        return deletedEvent;
    }
}
