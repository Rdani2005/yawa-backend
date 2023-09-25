package com.rdani2005.yawa.accounts.service.domain.ports.output.repository;

import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import java.util.Optional;

/**
 * Repository interface for managing customer entities.
 */
public interface CustomerRepository {
    /**
     * Retrieve a customer by their unique identifier.
     *
     * @param customerId The unique identifier of the customer.
     * @return An optional containing the customer if found, or empty if not found.
     */
    Optional<Customer> getCustomerByCustomerId(CustomerId customerId);
    /**
     * Save a customer entity.
     *
     * @param customer The customer entity to save.
     * @return The saved customer entity.
     */
    Customer saveCustomer(Customer customer);
    /**
     * Delete a customer entity.
     *
     * @param customer The customer entity to delete.
     */
    void deleteCustomer(Customer customer);

}
