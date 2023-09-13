package com.rdani2005.yawa.accounts.service.domain.handlers.read;

import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.read.CustomerAccountsReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.read.MultipleAccountsResponse;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountDomainException;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.AccountRepository;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.AccountId;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The AccountReadCommandHandler class is responsible for handling account read commands.
 */
@Slf4j
@Component
public class AccountReadCommandHandler {
    private final AccountServiceDataMapper accountServiceDataMapper;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     * Constructs an instance of AccountReadCommandHandler with the required dependencies.
     *
     * @param accountServiceDataMapper The data mapper for account-related operations.
     * @param accountRepository        The repository for accessing account data.
     * @param customerRepository       The repository for accessing customer data.
     */
    public AccountReadCommandHandler(
            AccountServiceDataMapper accountServiceDataMapper,
            AccountRepository accountRepository,
            CustomerRepository customerRepository
    ) {
        this.accountServiceDataMapper = accountServiceDataMapper;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Reads a single account based on the provided account read command.
     *
     * @param accountReadCommand The account read command.
     * @return An AccountReadResponse containing the account data.
     */
    @Transactional(readOnly = true)
    public AccountReadResponse readSingleAccount(AccountReadCommand accountReadCommand) {
        Optional<Account> accountResponse = accountRepository.getAccountById(
                new AccountId(accountReadCommand.getAccountId())
        );

        if (accountResponse.isEmpty()) {
            log.error("Account with id: {} was not found.", accountReadCommand.getAccountId());
            throw new AccountDomainException(
                    "Account with id: " + accountReadCommand.getAccountId() + " was not found."
            );
        }

        AccountReadResponse accountReadResponse =
                accountServiceDataMapper.accountToAccountReadResponse(accountResponse.get());
        log.info("Returning AccountReadResponse with id: {}", accountReadResponse.getAccountId());
        return accountReadResponse;
    }

    /**
     * Retrieves multiple accounts for a customer based on the provided customer accounts read command.
     *
     * @param customerAccountsReadCommand The customer accounts read command.
     * @return A MultipleAccountsResponse containing the customer's accounts.
     */
    @Transactional(readOnly = true)
    public MultipleAccountsResponse getAccountsForACustomer(CustomerAccountsReadCommand customerAccountsReadCommand) {
        checkCustomer(customerAccountsReadCommand.getCustomerId());
        Optional<List<Account>> accountsResponse =
                accountRepository.getAccountsByCustomer(new CustomerId(customerAccountsReadCommand.getCustomerId()));

        if (accountsResponse.isEmpty()) {
            log.error("Customer with id: {} does not have any accounts", customerAccountsReadCommand.getCustomerId());
            throw new AccountDomainException(
                    "Customer with id: " + customerAccountsReadCommand.getCustomerId() + " does not have any accounts"
            );
        }
        List<Account> accounts = accountsResponse.get();
        MultipleAccountsResponse response = MultipleAccountsResponse
                .builder()
                .customerId(customerAccountsReadCommand.getCustomerId())
                .accounts(
                        accounts.stream().map(
                                accountServiceDataMapper::accountToAccountReadResponse
                        ).collect(Collectors.toList())
                )
                .build();
        log.info("Returning all accounts for customer with id: {}", customerAccountsReadCommand.getCustomerId());
        return response;
    }


    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.error("Could not find customer with customer id: {}", customerId);
            throw new AccountDomainException("Could not find customer with customer id " + customerId);
        }
    }
}
