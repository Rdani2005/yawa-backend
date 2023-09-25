package com.rdani2005.yawa.accounts.service.domain.ports.input.listener;

import com.rdani2005.yawa.accounts.service.domain.dto.messages.customer.CustomerModel;

/**
 * Interface for handling events related to the deletion of a customer.
 */
public interface CustomerDeletedEventListener {
    /**
     * Handles the event when a customer is deleted.
     *
     * @param customerModel The CustomerModel representing the deleted customer.
     */
    void deleteCustomer(CustomerModel customerModel);
}
