package com.rdani2005.yawa.accounts.service.domain;

import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.read.CustomerAccountsReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.read.MultipleAccountsResponse;
import com.rdani2005.yawa.accounts.service.domain.handlers.create.AccountCreateCommandHandler;
import com.rdani2005.yawa.accounts.service.domain.handlers.delete.AccountDeleteCommandHandler;
import com.rdani2005.yawa.accounts.service.domain.handlers.read.AccountReadCommandHandler;
import com.rdani2005.yawa.accounts.service.domain.ports.inputs.service.AccountApplicationService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class AccountApplicationServiceImpl implements AccountApplicationService {
   private final AccountCreateCommandHandler accountCreateCommandHandler;
   private final AccountDeleteCommandHandler accountDeleteCommandHandler;
   private final AccountReadCommandHandler accountReadCommandHandler;

    public AccountApplicationServiceImpl(
            AccountCreateCommandHandler accountCreateCommandHandler,
            AccountDeleteCommandHandler accountDeleteCommandHandler,
            AccountReadCommandHandler accountReadCommandHandler
    ) {
        this.accountCreateCommandHandler = accountCreateCommandHandler;
        this.accountDeleteCommandHandler = accountDeleteCommandHandler;
        this.accountReadCommandHandler = accountReadCommandHandler;
    }

    @Override
    public AccountCreateResponse createAccount(AccountCreateCommand accountCreateCommand) {
        return accountCreateCommandHandler.createAccount(accountCreateCommand);
    }

    @Override
    public AccountReadResponse readSingleAccount(AccountReadCommand accountReadCommand) {
        return accountReadCommandHandler.readSingleAccount(accountReadCommand);
    }

    @Override
    public MultipleAccountsResponse readAccountFromCustomer(
            CustomerAccountsReadCommand customerAccountsReadCommand
    ) {
        return accountReadCommandHandler.getAccountsForACustomer(customerAccountsReadCommand);
    }

    @Override
    public AccountDeleteResponse deleteAccount(AccountDeleteCommand accountDeleteCommand) {
        return accountDeleteCommandHandler.deleteAccount(accountDeleteCommand);
    }
}
