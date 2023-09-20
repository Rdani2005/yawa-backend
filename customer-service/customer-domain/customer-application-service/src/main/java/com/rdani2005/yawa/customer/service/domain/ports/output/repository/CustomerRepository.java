package com.rdani2005.yawa.customer.service.domain.ports.output.repository;

import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer createCustomer(Customer customer);
    Optional<Customer> getCustomerById(CustomerId customerId);
    Optional<Customer> getCustomerByIdentification(String identification);
    Optional<List<Customer>> getAllCustomers();
    void deleteCustomer(Customer customer);
}
