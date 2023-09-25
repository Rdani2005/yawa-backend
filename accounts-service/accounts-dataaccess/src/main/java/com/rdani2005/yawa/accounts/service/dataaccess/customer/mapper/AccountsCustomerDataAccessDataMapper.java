package com.rdani2005.yawa.accounts.service.dataaccess.customer.mapper;

import com.rdani2005.yawa.accounts.service.dataaccess.customer.entity.CustomerEntity;
import com.rdani2005.yawa.accounts.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping between customer domain objects and customer data access objects.
 */
@Component
public class AccountsCustomerDataAccessDataMapper {

    /**
     * Converts a customer entity from the data access layer to a customer domain object.
     *
     * @param customerEntity The customer entity from the data access layer.
     * @return A customer domain object.
     */
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer
                .builder()
                .id(new CustomerId(customerEntity.getCustomerId()))
                .identification(customerEntity.getIdentification())
                .name(customerEntity.getName())
                .lastName(customerEntity.getLastName())
                .build();
    }

    /**
     * Converts a customer domain object to a customer entity in the data access layer.
     *
     * @param customer The customer domain object.
     * @return A customer entity in the data access layer.
     */
    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity
                .builder()
                .customerId(customer.getId().getValue())
                .identification(customer.getIdentification())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .build();
    }
}
