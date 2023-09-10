package com.rdani2005.yawa.accounts.service.domain.entity;

import com.rdani2005.yawa.domain.entity.AggregateRoot;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

/**
 * Represents a Customer entity in the domain.
 */
public class Customer extends AggregateRoot<CustomerId> {
    /**
     * Default constructor for a Customer.
     */
    public Customer() {

    }

    /**
     * Constructor for a Customer with a specified customerId.
     *
     * @param customerId The unique identifier for the customer.
     */
    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
