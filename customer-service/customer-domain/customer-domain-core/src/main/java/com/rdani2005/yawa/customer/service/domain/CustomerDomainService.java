package com.rdani2005.yawa.customer.service.domain;

import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;
import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;

/**
 * Defines methods for managing customer-related operations within the domain.
 */
public interface CustomerDomainService {

    /**
     * Validates and initializes a customer entity.
     *
     * @param customer The customer entity to be validated and initialized.
     * @return A {@link CustomerCreatedEvent} representing the event of creating a customer
     *         if the validation and initialization are successful.
     */
    CustomerCreatedEvent validateAndInitializeCustomer(Customer customer);

    /**
     * Deletes a customer entity.
     *
     * @param customer The customer entity to be deleted.
     * @return A {@link CustomerDeletedEvent} representing the event of deleting a customer
     *         if the deletion is successful.
     */
    CustomerDeletedEvent deleteCustomer(Customer customer);
}
