package com.rdani2005.yawa.accounts.service.domain.handlers.create;

import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.create.AccountCreateResponse;
import com.rdani2005.yawa.accounts.service.domain.event.AccountCreatedEvent;
import com.rdani2005.yawa.accounts.service.domain.handlers.AccountSagaHelper;
import com.rdani2005.yawa.accounts.service.domain.mapper.AccountServiceDataMapper;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.account.created.AccountCreatedPublisherEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The AccountCreateCommandHandler class is responsible for handling account creation commands.
 * It coordinates with the AccountCreateHelper to create and persist accounts.
 */
@Slf4j
@Component
public class AccountCreateCommandHandler {
    private final AccountCreateHelper accountCreateHelper;
    private final AccountServiceDataMapper accountServiceDataMapper;
    private final AccountCreatedPublisherEvent accountCreatedPublisherEvent;

    /**
     * Constructor for the AccountCreateCommandHandler class.
     *
     * @param accountCreateHelper           The helper class for creating and persisting accounts.
     * @param accountServiceDataMapper      The mapper for mapping data between DTOs and entities.
     * @param accountCreatedPublisherEvent  The publisher for publish the created event.
     */
    public AccountCreateCommandHandler(
            AccountCreateHelper accountCreateHelper,
            AccountServiceDataMapper accountServiceDataMapper,
            AccountSagaHelper accountSagaHelper,
            AccountCreatedPublisherEvent accountCreatedPublisherEvent) {
        this.accountCreateHelper = accountCreateHelper;
        this.accountServiceDataMapper = accountServiceDataMapper;
        this.accountCreatedPublisherEvent = accountCreatedPublisherEvent;
    }

    /**
     * Creates and persists an account based on the provided account creation command.
     *
     * @param accountCreateCommand The command containing account creation data.
     * @return An AccountCreateResponse indicating the result of the account creation.
     */
    @Transactional
    public AccountCreateResponse createAccount(AccountCreateCommand accountCreateCommand) {
        AccountCreatedEvent accountCreatedEvent = accountCreateHelper.persistAccount(accountCreateCommand);
        log.info("Account is created with id: {}", accountCreatedEvent.getAccount().getId());
        accountCreatedPublisherEvent.publish(accountCreatedEvent);
        AccountCreateResponse accountCreateResponse = accountServiceDataMapper.accountToAccountCreateResponse(
                accountCreatedEvent.getAccount(),
                "Account was created successfully"
        );
        log.info("Returning AccountCreateResponse with account id: {}", accountCreateResponse.getAccountId());
        return accountCreateResponse;
    }
}
