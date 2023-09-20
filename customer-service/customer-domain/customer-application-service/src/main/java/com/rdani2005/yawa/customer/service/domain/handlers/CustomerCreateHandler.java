package com.rdani2005.yawa.customer.service.domain.handlers;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.event.CustomerCreatedEvent;
import com.rdani2005.yawa.customer.service.domain.helpers.CustomerCreateHelper;
import com.rdani2005.yawa.customer.service.domain.mapper.CustomerDataMapper;
import com.rdani2005.yawa.customer.service.domain.ports.output.message.publisher.CustomerCreateMessagePublisher;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * The {@code CustomerCreateHandler} class handles the creation of customer entities, including validation and
 * initialization, and interacts with helper components, mappers, and message publishers.
 */
@Slf4j
@Component
public class CustomerCreateHandler {
    private final CustomerCreateHelper customerCreateHelper;
    private final CustomerDataMapper customerDataMapper;
    private final CustomerCreateMessagePublisher customerCreateMessagePublisher;

    /**
     * Constructs a new {@code CustomerCreateHandler} with the specified dependencies.
     *
     * @param customerCreateHelper           The helper component responsible for customer creation.
     * @param customerDataMapper             The data mapper for converting between DTOs and entities.
     * @param customerCreateMessagePublisher The message publisher for customer creation events.
     */
    public CustomerCreateHandler(
            CustomerCreateHelper customerCreateHelper,
            CustomerDataMapper customerDataMapper,
            CustomerCreateMessagePublisher customerCreateMessagePublisher
    ) {
        this.customerCreateHelper = customerCreateHelper;
        this.customerDataMapper = customerDataMapper;
        this.customerCreateMessagePublisher = customerCreateMessagePublisher;
    }

    /**
     * Creates a new customer based on the provided {@link CustomerCreateCommandDto} and initiates the customer
     * creation process. It also publishes a customer creation event.
     *
     * @param customerCreateCommandDto The DTO containing customer creation information.
     * @return A {@link CustomerCreateResponseDto} representing the response to the customer creation request.
     */
    @Transactional
    public CustomerCreateResponseDto createCustomer(
            CustomerCreateCommandDto customerCreateCommandDto
    ) {
        CustomerCreatedEvent createdEvent = customerCreateHelper.persistCustomer(customerCreateCommandDto);
        log.info("Customer was created with id: {}", createdEvent.getCustomer().getId().getValue());
        CustomerCreateResponseDto responseDto = customerDataMapper.customerToCreateCustomerResponse(
                createdEvent.getCustomer(),
                "Customer was created successfully"
        );
        customerCreateMessagePublisher.publish(createdEvent);
        return responseDto;
    }
}
