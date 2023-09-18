package com.rdani2005.yawa.customer.service.dataaccess.customer.mapper;

import com.rdani2005.yawa.customer.service.dataaccess.customer.entity.CustomerEntity;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

/**
 * The {@code CustomerDataAccessMapper} class is responsible for mapping between the data access layer's {@link CustomerEntity}
 * and the domain layer's {@link Customer} objects. It provides methods to convert a {@link CustomerEntity} to a {@link Customer}
 * and vice versa.
 */
@Component
public class CustomerDataAccessMapper {
    /**
     * Converts a {@link CustomerEntity} to a {@link Customer}.
     *
     * @param customerEntity The {@link CustomerEntity} to be converted.
     * @return A {@link Customer} object representing the same data as the input entity.
     */
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return Customer
                .builder()
                .id(new CustomerId(customerEntity.getId()))
                .name(customerEntity.getName())
                .lastName(customerEntity.getLastName())
                .identification(customerEntity.getIdentification())
                .birthDay(customerEntity.getBirthDay())
                .createdAt(customerEntity.getCreatedAt())
                .build();
    }

    /**
     * Converts a {@link Customer} to a {@link CustomerEntity}.
     *
     * @param customer The {@link Customer} to be converted.
     * @return A {@link CustomerEntity} object representing the same data as the input customer.
     */
    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity
                .builder()
                .id(customer.getId().getValue())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .identification(customer.getIdentification())
                .birthDay(customer.getBirthDay())
                .createdAt(customer.getCreatedAt())
                .build();
    }
}
