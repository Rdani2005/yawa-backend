package com.rdani2005.yawa.accounts.service.domain.handlers.delete;

import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteCommand;
import com.rdani2005.yawa.accounts.service.domain.dto.delete.AccountDeleteResponse;
import com.rdani2005.yawa.accounts.service.domain.event.AccountDeletedEvent;
import com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.account.deleted.AccountDeletedPublisherEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The AccountDeleteCommandHandler class is responsible for handling account deletion commands.
 */
@Slf4j
@Component
public class AccountDeleteCommandHandler {
    private final AccountDeleteHelper accountDeleteHelper;
    private final AccountDeletedPublisherEvent accountDeletedPublisherEvent;

    /**
     * Constructs an instance of AccountDeleteCommandHandler with the required dependencies.
     *
     * @param accountDeleteHelper           The helper class for account deletion operations.
     * @param accountDeletedPublisherEvent  The event publisher for account deletion events.
     */
    public AccountDeleteCommandHandler(
            AccountDeleteHelper accountDeleteHelper,
            AccountDeletedPublisherEvent accountDeletedPublisherEvent
    ) {
        this.accountDeleteHelper = accountDeleteHelper;
        this.accountDeletedPublisherEvent = accountDeletedPublisherEvent;
    }

    /**
     * Deletes an account based on the provided account deletion command.
     *
     * @param accountDeleteCommand The account deletion command.
     * @return An AccountDeleteResponse indicating the result of the deletion operation.
     */
    @Transactional
    public AccountDeleteResponse deleteAccount(AccountDeleteCommand accountDeleteCommand) {
        AccountDeletedEvent deletedEvent = accountDeleteHelper.deleteAccount(accountDeleteCommand);
        log.info("Deleting account with id: {}", deletedEvent.getAccount().getId());
        AccountDeleteResponse response = new AccountDeleteResponse("Account was deleted successfully.");
        accountDeletedPublisherEvent.publish(deletedEvent);
        return response;
    }
}
