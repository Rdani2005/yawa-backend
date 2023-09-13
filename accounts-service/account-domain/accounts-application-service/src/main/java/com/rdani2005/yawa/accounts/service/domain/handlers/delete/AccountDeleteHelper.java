package com.rdani2005.yawa.accounts.service.domain.handlers.delete;

import com.rdani2005.yawa.accounts.service.domain.AccountDomainService;
import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.event.AccountDeletedEvent;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.AccountRepository;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The AccountDeleteHelper class is responsible for assisting in the deletion of accounts.
 * It coordinates with the AccountDomainService and AccountRepository to delete accounts.
 */
@Slf4j
@Component
public class AccountDeleteHelper {
    private final AccountDomainService accountDomainService;
    private final AccountRepository accountRepository;

    /**
     * Constructor for the AccountDeleteHelper class.
     *
     * @param accountDomainService The domain service for account-related operations.
     * @param accountRepository    The repository for accessing account data.
     */
    public AccountDeleteHelper(
            AccountDomainService accountDomainService,
            AccountRepository accountRepository
    ) {
        this.accountDomainService = accountDomainService;
        this.accountRepository = accountRepository;
    }

    /**
     * Deletes an account based on the provided account delete command.
     *
     * @param accountDeleteCommand The command containing account deletion data.
     * @return An AccountDeletedEvent indicating the result of the account deletion.
     */
    @Transactional
    public AccountDeletedEvent deleteAccount(AccountDeleteCommand accountDeleteCommand) {
        Optional<Account> accountResponse =
                accountRepository.getAccountById(new AccountId(accountDeleteCommand.getAccountId()));

        if (accountResponse.isEmpty()) {
            log.error("Account with id: {} was not found.", accountDeleteCommand.getAccountId());
            throw new AccountDomainException(
                    "Account with id: " + accountDeleteCommand.getAccountId() + " was not found."
            );
        }
        Account account = accountResponse.get();
        AccountDeletedEvent accountDeletedEvent = accountDomainService.deleteAccount(account);
        accountRepository.deleteAccount(account);
        log.info("Account was deleted successfully");
        return accountDeletedEvent;
    }
}
