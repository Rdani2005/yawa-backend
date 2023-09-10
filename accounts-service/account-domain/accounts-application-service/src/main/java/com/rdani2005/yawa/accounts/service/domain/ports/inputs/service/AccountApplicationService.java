package com.rdani2005.yawa.accounts.service.domain.ports.inputs.service;

import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.read.CustomerAccountsReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.read.MultipleAccountsResponse;

import javax.validation.Valid;

/**
 * Service interface defining operations related to accounts.
 */
public interface AccountApplicationService {
    /**
     * Creates an account based on the provided account creation command.
     *
     * @param accountCreateCommand The command containing account creation details.
     * @return An account creation response.
     */
    AccountCreateResponse createAccount(
            @Valid AccountCreateCommand accountCreateCommand
    );

    /**
     * Reads details of a single account based on the provided account read command.
     *
     * @param accountReadCommand The command specifying the account to be read.
     * @return An account read response.
     */
    AccountReadResponse readSingleAccount(
            @Valid AccountReadCommand accountReadCommand
    );

    /**
     * Reads accounts associated with a customer based on the provided customer account read command.
     *
     * @param customerAccountsReadCommand The command specifying the customer and accounts to be read.
     * @return A response containing multiple accounts.
     */
    MultipleAccountsResponse readAccountFromCustomer(
            @Valid CustomerAccountsReadCommand customerAccountsReadCommand
    );

    /**
     * Deletes an account based on the provided account delete command.
     *
     * @param accountDeleteCommand The command specifying the account to be deleted.
     * @return An account delete response.
     */
    AccountDeleteResponse deleteAccount(
            @Valid AccountDeleteCommand accountDeleteCommand
    );
}
