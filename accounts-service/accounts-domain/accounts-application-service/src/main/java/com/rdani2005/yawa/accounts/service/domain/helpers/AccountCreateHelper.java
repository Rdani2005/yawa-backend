package com.rdani2005.yawa.accounts.service.domain.helpers;

import com.rdani2005.yawa.accounts.service.domain.AccountsDomainService;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.event.AccountsCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountsDomainException;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountsServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.AccountsRepository;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Helper class for creating accounts.
 */
@Slf4j
@Component
public class AccountCreateHelper {
    private final AccountsDomainService accountsDomainService;
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final AccountsServiceDataMapper accountsServiceDataMapper;

    public AccountCreateHelper(
            AccountsDomainService accountsDomainService,
            AccountsRepository accountsRepository,
            CustomerRepository customerRepository,
            AccountsServiceDataMapper accountsServiceDataMapper
    ) {
        this.accountsDomainService = accountsDomainService;
        this.accountsRepository = accountsRepository;
        this.customerRepository = customerRepository;
        this.accountsServiceDataMapper = accountsServiceDataMapper;
    }

    /**
     * Creates a new account based on the provided account creation command.
     *
     * @param accountCreateCommand The account creation command.
     * @return The created accounts' event.
     */
    @Transactional
    public AccountsCreatedEvent createAccount(AccountCreateCommand accountCreateCommand) {
        checkCustomerExistence(accountCreateCommand.getCustomerId());
        checkAccountExistance(accountCreateCommand.getAccountNumber());
        Account account = accountsServiceDataMapper.accountCreateCommandToAccount(accountCreateCommand);
        AccountsCreatedEvent createdEvent = accountsDomainService.createAccount(account);
        log.info("Saving account with id: {}", createdEvent.getAccount().getId().getValue());
        Account savedAccount = accountsRepository.saveAccount(createdEvent.getAccount());
        verifyAccountCreation(savedAccount, createdEvent);
        log.info("Account with id: {} was saved", createdEvent.getAccount().getId().getValue());
        return createdEvent;
    }


    private static void verifyAccountCreation(Account savedAccount, AccountsCreatedEvent createdEvent) {
        if (savedAccount == null) {
            log.error("Could not save account with account number: {}",  createdEvent.getAccount().getAccountNumber());
            throw new AccountsDomainException(
                    "Could not save account with account number: " +  createdEvent.getAccount().getAccountNumber()
            );
        }
    }

    /**
     * Checks the existence of a customer based on the provided customer ID.
     *
     * @param customerId The customer ID.
     */
    @Transactional(readOnly = true)
    public void checkCustomerExistence(UUID customerId) {
        Optional<Customer> customerResponse =
                customerRepository.getCustomerByCustomerId(new CustomerId(customerId));
        if (customerResponse.isEmpty()) {
            throw new AccountsDomainException("Customer with id: " + customerId + " does not exists on current context");
        }
    }

    /**
     * Checks the existence of an account based on the provided account number.
     *
     * @param accountNumber The account number.
     */
    @Transactional(readOnly = true)
    public void checkAccountExistance(String accountNumber) {
        Optional<Account> accountResponse =
                accountsRepository.readAccountByAccountNumber(accountNumber);
        if (accountResponse.isPresent()) {
            throw new AccountsDomainException("There was already an account with number: " + accountNumber);
        }
    }
}
