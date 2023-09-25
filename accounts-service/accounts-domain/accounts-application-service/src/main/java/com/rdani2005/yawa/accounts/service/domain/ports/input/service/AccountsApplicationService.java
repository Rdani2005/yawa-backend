package com.rdani2005.yawa.accounts.service.domain.ports.input.service;

import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.MultipleAccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.MultipleAccountsReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.SingleAccountReadCommand;

/**
 * Interface for managing account-related operations.
 */
public interface AccountsApplicationService {

    /**
     * Creates a new account based on the provided command.
     *
     * @param accountCreateCommand The command to create an account.
     * @return The response containing details of the created account.
     */
    AccountCreateResponse createAccount(AccountCreateCommand accountCreateCommand);
    /**
     * Deletes an account based on the provided command.
     *
     * @param accountDeleteCommand The command to delete an account.
     * @return The response indicating the result of the deletion.
     */
    AccountDeleteResponse deleteAccount(AccountDeleteCommand accountDeleteCommand);
    /**
     * Reads multiple accounts associated with a customer.
     *
     * @param multipleAccountsReadCommand The command to retrieve multiple accounts for a customer.
     * @return The response containing a list of account details.
     */
    MultipleAccountReadResponse readAccountsByCustomer(MultipleAccountsReadCommand multipleAccountsReadCommand);

    /**
     * Reads a single account based on the provided command.
     *
     * @param singleAccountReadCommand The command to retrieve a single account.
     * @return The response containing details of the specified account.
     */
    AccountReadResponse readAccount(SingleAccountReadCommand singleAccountReadCommand);


}
