package com.rdani2005.yawa.customer.service.domain.mapper;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.rdani2005.yawa.customer.service.domain.entity.Customer.UTC;

/**
 * The {@code CustomerDataMapper} class is responsible for mapping between different representations of customer data.
 * It provides methods for converting DTOs (Data Transfer Objects) to entity objects and vice versa.
 */
@Component
public class CustomerDataMapper {
    /**
     * Converts a {@link CustomerCreateCommandDto} object into a {@link Customer} entity.
     *
     * @param customerCreateCommandDto The DTO representing customer creation data.
     * @return A {@link Customer} entity initialized with the data from the DTO.
     */
    public Customer createCustomerCommandToCustomer(
            CustomerCreateCommandDto customerCreateCommandDto
    ) {
        return Customer
                .builder()
                .name(customerCreateCommandDto.getName())
                .lastName(customerCreateCommandDto.getLastName())
                .lastName(customerCreateCommandDto.getLastName())
                .identification(customerCreateCommandDto.getIdentification())
                .birthDay(customerCreateCommandDto.getBirthDay())
                .createdAt(ZonedDateTime.now(ZoneId.of(UTC)))
                .build();
    }

    /**
     * Maps a {@link Customer} entity to a {@link CustomerCreateResponseDto} DTO with a message.
     *
     * @param customer The {@link Customer} entity to be mapped.
     * @param message  A message associated with the mapping.
     * @return A {@link CustomerCreateResponseDto} containing customer data and the provided message.
     */
    public CustomerCreateResponseDto customerToCreateCustomerResponse(
            Customer customer,
            String message
    ) {
        return CustomerCreateResponseDto
                .builder()
                .customer(customerToCustomerReadResponse(customer))
                .message(message)
                .build();
    }

    /**
     * Maps a {@link Customer} entity to a {@link CustomerReadResponseDto} DTO.
     *
     * @param customer The {@link Customer} entity to be mapped.
     * @return A {@link CustomerReadResponseDto} containing customer data.
     */
    public CustomerReadResponseDto customerToCustomerReadResponse(
            Customer customer
    ) {
        return CustomerReadResponseDto
                .builder()
                .id(customer.getId().getValue())
                .name(customer.getName())
                .lastName(customer.getLastName())
                .identification(customer.getIdentification())
                .birthDay(customer.getBirthDay())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    /**
     * Maps a list of {@link Customer} entities to a list of {@link CustomerReadResponseDto} DTOs.
     *
     * @param customers The list of {@link Customer} entities to be mapped.
     * @return A list of {@link CustomerReadResponseDto} DTOs containing customer data.
     */
    public List<CustomerReadResponseDto> customersToCustomerReadResponse(
            List<Customer> customers
    ) {
        return customers
                .stream()
                .map(this::customerToCustomerReadResponse)
                .collect(Collectors.toList());
    }
}
