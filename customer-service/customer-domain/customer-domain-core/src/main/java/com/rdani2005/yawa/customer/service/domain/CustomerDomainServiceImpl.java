package com.rdani2005.yawa.customer.service.domain;

import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;
import com.rdani2005.yawa.customer.service.domain.event.CustomerDeletedEvent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.rdani2005.yawa.customer.service.domain.entity.Customer.UTC;

/**
 * Implementation of the {@link CustomerDomainService} interface for managing customer-related operations.
 */
public class CustomerDomainServiceImpl implements CustomerDomainService {

    /**
     * Validates and initializes a customer entity.
     *
     * @param customer The customer entity to be validated and initialized.
     * @return A {@link CustomerCreatedEvent} representing the event of creating a customer
     * if the validation and initialization are successful.
     */
    @Override
    public CustomerCreatedEvent validateAndInitializeCustomer(Customer customer) {
        customer.initializeCustomer();
        return new CustomerCreatedEvent(
                customer,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }

    /**
     * Deletes a customer entity.
     *
     * @param customer The customer entity to be deleted.
     * @return A {@link CustomerDeletedEvent} representing the event of deleting a customer
     * if the deletion is successful.
     */
    @Override
    public CustomerDeletedEvent deleteCustomer(Customer customer) {
        return new CustomerDeletedEvent(
                customer,
                ZonedDateTime.now(ZoneId.of(UTC))
        );
    }
}
