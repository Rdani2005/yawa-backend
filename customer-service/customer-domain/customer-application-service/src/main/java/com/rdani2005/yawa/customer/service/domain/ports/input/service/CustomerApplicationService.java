package com.rdani2005.yawa.customer.service.domain.ports.input.service;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadIdentificationCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.MultiCustomerReadResponseDto;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

/**
 * Interface defining the application service methods for customer-related operations.
 */
public interface CustomerApplicationService {
    /**
     * Create a new customer based on the provided command DTO.
     *
     * @param customerCreateCommandDto The command DTO containing customer creation information.
     * @return A response DTO containing information about the created customer.
     */
    CustomerCreateResponseDto createCustomer(CustomerCreateCommandDto customerCreateCommandDto);

    /**
     * Delete a customer based on the provided command DTO.
     *
     * @param customerDeleteCommandDto The command DTO containing customer deletion information.
     * @return A response DTO indicating the result of the customer deletion operation.
     */
    CustomerDeleteResponseDto deleteCustomer(CustomerDeleteCommandDto customerDeleteCommandDto);

    /**
     * Get a customer by their ID.
     *
     * @param customerReadDto The DTO containing the customer ID for retrieval.
     * @return A response DTO containing information about the retrieved customer.
     */
    CustomerReadResponseDto getCustomerById(CustomerReadDto customerReadDto);

    /**
     * Get a customer by their identification information.
     *
     * @param customerReadIdentificationCommandDto The command DTO containing identification information.
     * @return A response DTO containing information about the retrieved customer.
     */
    CustomerReadResponseDto getCustomerByIdentification(
            CustomerReadIdentificationCommandDto customerReadIdentificationCommandDto
    );

    /**
     * Get a list of all customers.
     *
     * @return A response DTO containing a list of customer information.
     */
    MultiCustomerReadResponseDto getAllCustomers();

    /**
     * Get a list of all customers with applied filters.
     *
     * @param name              The customer name filter (optional).
     * @param initialCreatedDate The initial created date filter (optional).
     * @param finalCreatedDate   The final created date filter (optional).
     * @return A response DTO containing a list of customer information.
     */
    MultiCustomerReadResponseDto getAllCustomersWithFilters(
            @Nullable String name,
            @Nullable ZonedDateTime initialCreatedDate,
            @Nullable ZonedDateTime finalCreatedDate,
            @Nullable ZonedDateTime birthday
    );
}
