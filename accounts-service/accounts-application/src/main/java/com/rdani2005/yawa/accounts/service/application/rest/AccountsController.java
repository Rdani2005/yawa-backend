package com.rdani2005.yawa.accounts.service.application.rest;

import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.AccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.MultipleAccountReadResponse;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.MultipleAccountsReadCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.api.read.SingleAccountReadCommand;
import com.rdani2005.yawa.accounts.service.domain.ports.input.service.AccountsApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/accounts", produces = "application/vnd.api.v1+json")
public class AccountsController {
    private final AccountsApplicationService accountsApplicationService;

    public AccountsController(
            AccountsApplicationService accountsApplicationService
    ) {
        this.accountsApplicationService = accountsApplicationService;
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<MultipleAccountReadResponse> getAccountsByCustomer(
            @PathVariable UUID customerId
    ) {
        MultipleAccountReadResponse multipleAccountReadResponse =
                accountsApplicationService.readAccountsByCustomer(
                        MultipleAccountsReadCommand
                                .builder()
                                .customerId(customerId)
                                .build()
                );
        log.info("Returning all accounts for customer with id: {}", multipleAccountReadResponse.getCustomerId());
        return ResponseEntity.ok(multipleAccountReadResponse);
    }

    @GetMapping("/{customerId}/account/{accountId}")
    public ResponseEntity<AccountReadResponse> getAccountByAccountId(
            @PathVariable UUID customerId,
            @PathVariable UUID accountId
    ) {
        AccountReadResponse accountReadResponse =
                accountsApplicationService.readAccount(
                        SingleAccountReadCommand.builder()
                                .accountId(accountId)
                                .build()
                );
        log.info("Returning account with id: {}", accountReadResponse.getAccountId());
        return ResponseEntity.ok(accountReadResponse);
    }

    @DeleteMapping("/{customerId}/account/{accountId}")
    public ResponseEntity<AccountDeleteResponse> deleteAccountByAccountId(
            @PathVariable UUID customerId,
            @PathVariable UUID accountId
    ) {
        log.info("Deleting account with id: {}", accountId);
        AccountDeleteResponse accountDeleteResponse =
                accountsApplicationService.deleteAccount(
                        AccountDeleteCommand.builder()
                                .accountId(accountId)
                                .build()
                );
        log.info("Account was deleted.");
        return ResponseEntity.ok(accountDeleteResponse);
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<AccountCreateResponse> createAccount(
            @PathVariable UUID customerId,
            @RequestBody AccountCreateCommand accountCreateCommand
    ) {
        log.info("Creating account with account number: {}", accountCreateCommand.getAccountNumber());
        AccountCreateResponse createResponse = accountsApplicationService.createAccount(accountCreateCommand);
        log.info("account created with id: {}", createResponse.getAccountId());
        return ResponseEntity.ok(createResponse);
    }
}
