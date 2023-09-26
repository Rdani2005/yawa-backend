package com.rdani2005.yawa.accounts.service.domain.handlers;

import com.rdani2005.yawa.accounts.service.domain.dto.api.read.*;
import com.rdani2005.yawa.accounts.service.domain.entity.Account;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountNotFoundException;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountsServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.AccountsRepository;
import com.rdani2005.yawa.accounts.service.domain.ports.output.repository.CustomerRepository;
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
 * Handler for reading accounts and account-related data.
 */
@Slf4j
@Component
public class AccountReadHandler {
    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final AccountsServiceDataMapper dataMapper;

    /**
     * Constructs an AccountReadHandler with the necessary dependencies.
     *
     * @param customerRepository     The repository for accessing customer data.
     * @param accountsRepository     The repository for accessing account data.
     * @param dataMapper             The data mapper for mapping between DTOs and entities.
     */
    public AccountReadHandler(
            CustomerRepository customerRepository,
            AccountsRepository accountsRepository,
            AccountsServiceDataMapper dataMapper
    ) {
        this.customerRepository = customerRepository;
        this.accountsRepository = accountsRepository;
        this.dataMapper = dataMapper;
    }

    /**
     * Reads all accounts for a specific customer.
     *
     * @param multipleCustomerAccountsReadCommand The command containing customer ID.
     * @return The response containing customer and account information.
     */
    @Transactional(readOnly = true)
    public MultipleAccountReadResponse readAccountsByCustomer(
            MultipleCustomerAccountsReadCommand multipleCustomerAccountsReadCommand
    ) {
        log.info("Requested to read all accounts for customer: {}", multipleCustomerAccountsReadCommand.getCustomerId());
        Customer customer = verifyCustomerExistence(multipleCustomerAccountsReadCommand.getCustomerId());
        Optional<List<Account>> customerAccounts = accountsRepository.readAccountsByCustomerId(
                new CustomerId(multipleCustomerAccountsReadCommand.getCustomerId())
        );

        if (customerAccounts.isEmpty()) {
            log.error("Customer with id: {} has no accounts.", customer.getId().getValue());
            throw new AccountNotFoundException("Customer with id: " + customer.getId().getValue() + " has no accounts.");
        }

        return MultipleAccountReadResponse
                .builder()
                .customerId(customer.getId().getValue())
                .identification(customer.getIdentification())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .accounts(dataMapper.accountsToReadResponse(customerAccounts.get()))
                .build();
    }

    /**
     * Reads all customer and their accounts
     *
     * @return the response with its information
     */
    @Transactional(readOnly = true)
    public AllCustomerAccountsReadResponse readAllCustomersWithAccounts() {
        Optional<List<Customer>> customers = customerRepository.getAllCustomers();
        if (customers.isEmpty()) {
            throw new AccountNotFoundException("There arent any customers on current context");
        }

        return AllCustomerAccountsReadResponse
                .builder()
                .customers(
                      customers.get().stream().map(
                              customer -> this.readAccountsByCustomer(
                                      MultipleCustomerAccountsReadCommand
                                              .builder()
                                              .customerId(customer.getId().getValue())
                                              .build())
                      ).collect(Collectors.toList())
                )
                .build();
    }

    /**
     * Reads account details by account ID.
     *
     * @param accountReadCommand The command containing account ID.
     * @return The response containing account details.
     */
    @Transactional(readOnly = true)
    public AccountReadResponse readAccountByAccountId(SingleAccountReadCommand accountReadCommand) {
        Optional<Account> account = accountsRepository.readAccountByAccountId(
                new AccountId(accountReadCommand.getAccountId())
        );

        if (account.isEmpty()) {
            log.error("Account with id: {} was not found.", accountReadCommand.getAccountId());
            throw new AccountNotFoundException(
                    "Account with id: " + accountReadCommand.getAccountId() + " was not found."
            );
        }

        return dataMapper.accountToAccountReadResponse(account.get());
    }

    private Customer verifyCustomerExistence(UUID customerId) {
        Optional<Customer> customer = customerRepository.getCustomerByCustomerId(
                new CustomerId(customerId)
        );

        if (customer.isEmpty()) {
            throw new AccountNotFoundException("Customer with id: " + customerId + " was not found on current context");
        }

        return  customer.get();
    }
}
