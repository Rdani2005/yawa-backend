package com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.account.deleted;

import com.rdani2005.yawa.accounts.service.domain.event.AccountDeletedEvent;

/**
 * The AccountDeletedPublisherEvent interface defines the contract for publishing account deletion events.
 */
public interface AccountDeletedPublisherEvent {
    /**
     * Publishes an account deletion event.
     *
     * @param accountEvent The account event to be published.
     */
    void publish(AccountDeletedEvent accountEvent);
}
