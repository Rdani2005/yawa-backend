package com.rdani2005.yawa.accounts.service.domain.handlers.create;

import com.rdani2005.yawa.accounts.service.domain.AccountDomainService;
import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.event.AccountCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.AccountRepository;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.Money;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * The AccountCreateHelper class provides helper methods for creating and persisting accounts.
 * It interacts with the Account Domain Service, repositories, and mappers to create and save accounts.
 */
@Slf4j
@Component
public class AccountCreateHelper {
    private final AccountDomainService accountDomainService;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountServiceDataMapper accountServiceDataMapper;

    /**
     * Constructor for the AccountCreateHelper class.
     *
     * @param accountDomainService   The Account Domain Service for initializing accounts.
     * @param accountRepository      The repository for accessing and saving account data.
     * @param customerRepository     The repository for accessing customer data.
     * @param accountServiceDataMapper The mapper for mapping data between DTOs and entities.
     */
    public AccountCreateHelper(
            AccountDomainService accountDomainService,
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            AccountServiceDataMapper accountServiceDataMapper
    ) {
        this.accountDomainService = accountDomainService;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.accountServiceDataMapper = accountServiceDataMapper;
    }

    /**
     * Persists an account by creating it using the provided account create command.
     *
     * @param accountCreateCommand The command containing account creation data.
     * @return An AccountCreatedEvent representing the account creation.
     */
    @Transactional
    public AccountCreatedEvent persistAccount(AccountCreateCommand accountCreateCommand) {
        checkCustomer(accountCreateCommand.getCustomerId());
        Account account = accountServiceDataMapper.createDtoToAccount(accountCreateCommand);
        AccountCreatedEvent accountCreatedEvent = accountDomainService.initializeAccount(
                account,
                new Money(accountCreateCommand.getInitialAmount())
        );
        saveAccount(account);
        log.info("Order is created with id: {}", accountCreatedEvent.getAccount().getId().getValue());
        return accountCreatedEvent;
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.error("Could not find customer with customer id: {}", customerId);
            throw new AccountDomainException("Could not find customer with customer id " + customerId);
        }
    }

    private Account saveAccount(Account account) {
        Account accountResult = accountRepository.createAccount(account);
        if (accountResult == null) {
            throw new AccountDomainException("Could not save account!");
        }
        log.info("Account is saved with id: {}", accountResult.getId().getValue());
        return accountResult;
    }
}
