package com.rdani2005.yawa.accounts.service.domain.ports.outputs.message.publisher.account.created;

import com.rdani2005.yawa.accounts.service.domain.event.AccountCreatedEvent;

/**
 * The AccountCreatedPublisherEvent interface defines the contract for publishing account creation events.
 */
public interface AccountCreatedPublisherEvent {
    /**
     * Publishes an account creation event.
     *
     * @param accountEvent The account event to be published.
     */
    void publish(AccountCreatedEvent accountEvent);
}
