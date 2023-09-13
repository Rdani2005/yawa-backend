package com.rdani2005.yawa.outbox;

/**
 * Interface representing a scheduler for processing outbox messages.
 * Outbox messages are asynchronous messages that need to be processed
 * and sent to a destination, typically used in distributed systems.
 */
public interface OutboxScheduler {
    /**
     * Process and send the outbox messages.
     * This method is responsible for retrieving pending outbox messages
     * and sending them to their respective destinations.
     */
    void processOutboxMessage();
}
