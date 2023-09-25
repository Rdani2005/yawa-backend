package com.rdani2005.yawa.accounts.service.domain.ports.input.listener;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.customer.CustomerModel;

/**
 * Interface for handling events related to the creation of a customer.
 */
public interface CustomerCreatedEventListener {
    /**
     * Handles the event when a customer is created.
     *
     * @param customerModel The CustomerModel representing the created customer.
     */
    void CustomerCreated(CustomerModel customerModel);
}
