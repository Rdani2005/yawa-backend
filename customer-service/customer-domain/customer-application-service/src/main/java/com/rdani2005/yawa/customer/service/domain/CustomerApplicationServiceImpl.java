package com.rdani2005.yawa.customer.service.domain;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadIdentificationCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.MultiCustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.handlers.CustomerCreateHandler;
import com.rdani2005.yawa.customer.service.domain.handlers.CustomerDeleteHandler;
import com.rdani2005.yawa.customer.service.domain.handlers.CustomerReadHandler;
import com.rdani2005.yawa.customer.service.domain.ports.input.service.CustomerApplicationService;

import org.springframework.stereotype.Component;

/**
 * The {@code CustomerApplicationServiceImpl} class provides an implementation of the {@link CustomerApplicationService}
 * interface for managing customer-related operations. It delegates the processing of these operations to specific handlers.
 */
@Component
public class CustomerApplicationServiceImpl implements CustomerApplicationService {
    private final CustomerCreateHandler customerCreateHandler;
    private final CustomerReadHandler customerReadHandler;
    private final CustomerDeleteHandler customerDeleteHandler;

    /**
     * Constructs a new {@code CustomerApplicationServiceImpl} with the specified dependencies.
     *
     * @param customerCreateHandler The handler for creating customer entities.
     * @param customerReadHandler   The handler for reading customer entities.
     * @param customerDeleteHandler The handler for deleting customer entities.
     */
    public CustomerApplicationServiceImpl(
            CustomerCreateHandler customerCreateHandler,
            CustomerReadHandler customerReadHandler,
            CustomerDeleteHandler customerDeleteHandler
    ) {
        this.customerCreateHandler = customerCreateHandler;
        this.customerReadHandler = customerReadHandler;
        this.customerDeleteHandler = customerDeleteHandler;
    }

    /**
     * Creates a new customer entity based on the provided create command.
     *
     * @param customerCreateCommandDto The DTO containing customer information for creation.
     * @return A {@link CustomerCreateResponseDto} indicating the success of the customer creation.
     */
    @Override
    public CustomerCreateResponseDto createCustomer(
            CustomerCreateCommandDto customerCreateCommandDto
    ) {
        return customerCreateHandler.createCustomer(customerCreateCommandDto);
    }

    /**
     * Deletes a customer entity based on the provided delete command.
     *
     * @param customerDeleteCommandDto The DTO containing the customer's unique identifier to be deleted.
     * @return A {@link CustomerDeleteResponseDto} indicating the success of the customer deletion.
     */
    @Override
    public CustomerDeleteResponseDto deleteCustomer(
            CustomerDeleteCommandDto customerDeleteCommandDto
    ) {
        return customerDeleteHandler.deleteCustomer(customerDeleteCommandDto);
    }

    /**
     * Retrieves customer information by the provided customer ID.
     *
     * @param customerReadDto The DTO containing the customer's unique identifier for retrieval.
     * @return A {@link CustomerReadResponseDto} containing the customer's information.
     */
    @Override
    public CustomerReadResponseDto getCustomerById(
            CustomerReadDto customerReadDto
    ) {
        return customerReadHandler.readCustomerById(customerReadDto);
    }

    /**
     * Retrieves customer information by the provided customer Identification.
     *
     * @param customerReadIdentificationCommandDto The DTO containing the customer's unique identification for retrieval.
     * @return A {@link CustomerReadResponseDto} containing the customer's information.
     */
    @Override
    public CustomerReadResponseDto getCustomerByIdentification(
            CustomerReadIdentificationCommandDto customerReadIdentificationCommandDto
    ) {
        return customerReadHandler.readCustomerByIdentification(customerReadIdentificationCommandDto);
    }

    /**
     * Retrieves information for all customers.
     *
     * @return A {@link MultiCustomerReadResponseDto} containing a list of customer information.
     */
    @Override
    public MultiCustomerReadResponseDto getAllCustomers() {
        return customerReadHandler.readAllCustomers();
    }
}
