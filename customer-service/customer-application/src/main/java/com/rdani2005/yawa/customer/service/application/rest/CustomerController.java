package com.rdani2005.yawa.customer.service.application.rest;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadIdentificationCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.MultiCustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.ports.input.service.CustomerApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;
/**
 * Controller for managing customer-related operations.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/customers", produces = "application/vnd.api.v1+json")
public class CustomerController {
    private final CustomerApplicationService customerApplicationService;

    public CustomerController(CustomerApplicationService customerApplicationService) {
        this.customerApplicationService = customerApplicationService;
    }

    /**
     * Create a new customer.
     *
     * @param customerCreateCommandDto The request body containing customer creation information.
     * @return ResponseEntity containing the created customer information.
     */
    @PostMapping
    public ResponseEntity<CustomerCreateResponseDto> createCustomer(
            @RequestBody CustomerCreateCommandDto customerCreateCommandDto
    ) {
        log.info("Creating customer with identification: {}", customerCreateCommandDto.getIdentification());
        CustomerCreateResponseDto customerCreateResponseDto =
                customerApplicationService.createCustomer(customerCreateCommandDto);
        log.info("Customer created with id: {}", customerCreateResponseDto.getCustomer().getId());
        return ResponseEntity.ok(customerCreateResponseDto);
    }

    /**
     * Retrieve a list of all customers.
     *
     * @return ResponseEntity containing a list of customer information.
     */
    @GetMapping
    public ResponseEntity<MultiCustomerReadResponseDto> getAllCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ZonedDateTime initialCreatedDate,
            @RequestParam(required = false) ZonedDateTime finalCreatedDate,
            @RequestParam(required = false) ZonedDateTime birthday
            ) {
        return ResponseEntity.ok(
                customerApplicationService.getAllCustomersWithFilters(
                        name,
                        initialCreatedDate,
                        finalCreatedDate,
                        birthday
                )
        );
    }

    /**
     * Retrieve a list of customers with applied filters.
     *
     * @return ResponseEntity containing a list of customer information.
     */
    @GetMapping
    public ResponseEntity<MultiCustomerReadResponseDto> getAllCustomers(

    ) {
        return ResponseEntity.ok(
                customerApplicationService.getAllCustomers()
        );
    }

    /**
     * Retrieve a customer by their ID.
     *
     * @param customerId The customer's ID provided as a path variable.
     * @return ResponseEntity containing the customer information.
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerReadResponseDto> getCustomerByCustomerId(
            @PathVariable UUID customerId
    ) {
        CustomerReadResponseDto customerReadResponseDto =
                customerApplicationService.getCustomerById(
                        CustomerReadDto
                                .builder()
                                .customerId(customerId)
                                .build()
                );
        log.info("Returning customer with id: {}", customerReadResponseDto.getId());
        return ResponseEntity.ok(
                customerReadResponseDto
        );
    }

    /**
     * Retrieve a customer by their identification.
     *
     * @param customerIdentification The customer's identification provided as a path variable.
     * @return ResponseEntity containing the customer information.
     */
    @GetMapping("/{customerIdentification}")
    public ResponseEntity<CustomerReadResponseDto> getCustomerByCustomerIdentification(
            @PathVariable String customerIdentification
    ) {
        CustomerReadResponseDto customerReadResponseDto =
                customerApplicationService.getCustomerByIdentification(
                        CustomerReadIdentificationCommandDto
                                .builder()
                                .identification(customerIdentification)
                                .build()
                );
        log.info("Returning customer with id: {}", customerReadResponseDto.getId());
        return ResponseEntity.ok(
                customerReadResponseDto
        );
    }

    /**
     * Delete a customer by their ID.
     *
     * @param customerId The customer's ID provided as a path variable.
     * @return ResponseEntity indicating the result of the customer deletion operation.
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerDeleteResponseDto> deleteCustomer(
            @PathVariable UUID customerId
    ) {
        log.info("deleting customer with id: {}", customerId);
        return ResponseEntity.ok(
                customerApplicationService.deleteCustomer(
                        CustomerDeleteCommandDto
                                .builder()
                                .customerId(customerId)
                                .build()
                )
        );
    }
}
