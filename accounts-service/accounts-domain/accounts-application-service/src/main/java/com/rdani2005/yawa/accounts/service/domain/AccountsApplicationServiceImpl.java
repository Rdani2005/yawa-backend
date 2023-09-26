package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.*;
import com.rdani2005.yawa.accounts.service.domain.handlers.AccountCreateHandler;
import com.rdani2005.yawa.accounts.service.domain.handlers.AccountDeleteHandler;
import com.rdani2005.yawa.accounts.service.domain.handlers.AccountReadHandler;
import com.rdani2005.yawa.accounts.service.domain.ports.input.service.AccountsApplicationService;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Implementation of the AccountsApplicationService interface.
 */
@Validated
@Service
public class AccountsApplicationServiceImpl implements AccountsApplicationService {

    private final AccountCreateHandler accountCreateHandler;
    private final AccountDeleteHandler accountDeleteHandler;
    private final AccountReadHandler accountReadHandler;

    /**
     * Constructs an AccountsApplicationServiceImpl with the necessary handlers.
     *
     * @param accountCreateHandler The handler for account creation.
     * @param accountDeleteHandler The handler for account deletion.
     * @param accountReadHandler   The handler for account reading.
     */
    public AccountsApplicationServiceImpl(
            AccountCreateHandler accountCreateHandler,
            AccountDeleteHandler accountDeleteHandler,
            AccountReadHandler accountReadHandler
    ) {
        this.accountCreateHandler = accountCreateHandler;
        this.accountDeleteHandler = accountDeleteHandler;
        this.accountReadHandler = accountReadHandler;
    }

    /**
     * @inheritDoc
     */
    @Override
    public AccountCreateResponse createAccount(
            AccountCreateCommand accountCreateCommand
    ) {
        return accountCreateHandler.createAccount(accountCreateCommand);
    }

    /**
     * @inheritDoc
     */
    @Override
    public AccountDeleteResponse deleteAccount(
            AccountDeleteCommand accountDeleteCommand
    ) {
        return accountDeleteHandler.deleteAccount(accountDeleteCommand);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MultipleAccountReadResponse readAccountsByCustomer(
            MultipleCustomerAccountsReadCommand multipleCustomerAccountsReadCommand
    ) {
        return accountReadHandler.readAccountsByCustomer(multipleCustomerAccountsReadCommand);
    }

    /**
     * @inheritDoc
     */
    @Override
    public AccountReadResponse readAccount(
            SingleAccountReadCommand singleAccountReadCommand
    ) {
        return accountReadHandler.readAccountByAccountId(singleAccountReadCommand);
    }

    /**
     * @inheritDoc
     */
    @Override
    public AllCustomerAccountsReadResponse readAllCustomersAccounts() {
        return accountReadHandler.readAllCustomersWithAccounts();
    }
}
