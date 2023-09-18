package com.rdani2005.yawa.customer.service.domain;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.entity.Customer;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerException;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerNotFoundException;
import com.rdani2005.yawa.customer.service.domain.mapper.CustomerDataMapper;
import com.rdani2005.yawa.customer.service.domain.ports.input.service.CustomerApplicationService;
import com.rdani2005.yawa.customer.service.domain.ports.output.repository.CustomerRepository;
import com.rdani2005.yawa.domain.valueobject.CustomerId;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The {@code CustomerApplicationServiceTest} class contains unit tests for the {@link CustomerApplicationService}
 * implementation, covering various scenarios related to customer creation, deletion, and retrieval.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = CustomerTestConfiguration.class)
public class CustomerApplicationServiceTest {
    @Autowired
    private CustomerApplicationService customerApplicationService;
    @Autowired
    private CustomerDataMapper customerDataMapper;
    @Autowired
    private CustomerRepository customerRepository;

    private CustomerCreateCommandDto customerCreateCommandDto;
    private  CustomerCreateCommandDto customerWithWrongAge;
    private final CustomerId CUSTOMER_ID = new CustomerId(
            UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")
    );
    private final CustomerId WRONG_CUSTOMER_ID = new CustomerId(
            UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb42")
    );

    private CustomerDeleteCommandDto deleteCommandDto;
    private CustomerDeleteCommandDto wrongDeleteCommandDto;
    private final ZonedDateTime rightBirthDay =
            ZonedDateTime.of(
                    2005,
                    Month.MAY.getValue(),
                    15,
                    0, 0, 0, 0,
                    ZoneId.of("UTC")
            );

    private final ZonedDateTime wrongBirthDay =
            ZonedDateTime.of(
                    2020,
                    Month.MAY.getValue(),
                    15,
                    0, 0, 0, 0,
                    ZoneId.of("UTC")
            );

    /**
     * Initializes test data and dependencies before running test methods.
     */
    @BeforeAll
    public void init() {
        customerCreateCommandDto = CustomerCreateCommandDto
                .builder()
                .name("Daniel")
                .lastName("Sequeira Campos")
                .identification("1-1935-0628")
                .birthDay(rightBirthDay)
                .build();

        customerWithWrongAge = CustomerCreateCommandDto
                .builder()
                .name("Daniel")
                .lastName("Sequeira Campos")
                .identification("1-1935-0628")
                .birthDay(wrongBirthDay)
                .build();

        deleteCommandDto = CustomerDeleteCommandDto
                .builder()
                .customerId(CUSTOMER_ID.getValue())
                .build();

        wrongDeleteCommandDto = CustomerDeleteCommandDto
                .builder()
                .customerId(WRONG_CUSTOMER_ID.getValue())
                .build();

        Customer customer = customerDataMapper.createCustomerCommandToCustomer(customerCreateCommandDto);
        customer.setId(CUSTOMER_ID);
        when(customerRepository.getCustomerById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(customerRepository.createCustomer(any(Customer.class))).thenReturn(customer);
        when(customerRepository.getAllCustomers()).thenReturn(Optional.of(List.of(customer)));
        doNothing().when(customerRepository).deleteCustomer(any(Customer.class));
    }

    /**
     * Tests the creation of a new customer entity.
     */
    @Test
    public void testCreateCustomer() {
        CustomerCreateResponseDto customerCreateResponseDto = customerApplicationService.createCustomer(
                customerCreateCommandDto
        );
        assertEquals("Customer was created successfully", customerCreateResponseDto.getMessage());
    }

    /**
     * Tests the creation of a customer entity with an incorrect birthdate, expecting an exception.
     */
    @Test
    public void testCreateUserWithWrongBirthDay() {
        CustomerException customerException = assertThrows(
                CustomerException.class,
                () -> customerApplicationService.createCustomer(customerWithWrongAge)
        );
        assertEquals("The client is underage", customerException.getMessage());
    }
    /**
     * Tests the deletion of a customer entity.
     */
    @Test
    public void testDeleteUser() {
        CustomerDeleteResponseDto customerDeleteResponse = customerApplicationService.deleteCustomer(deleteCommandDto);
        assertEquals("Customer was deleted successfully", customerDeleteResponse.getMessage());
    }

    /**
     * Tests the deletion of a customer entity with an incorrect customer ID, expecting an exception.
     */
    @Test
    public void testDeleteUserWithWrongId() {
        CustomerNotFoundException customerException = assertThrows(
                CustomerNotFoundException.class,
                () -> customerApplicationService.deleteCustomer(wrongDeleteCommandDto)
        );

        assertEquals(
                "Could not found customer with id: " + WRONG_CUSTOMER_ID.getValue(),
                customerException.getMessage()

        );
    }

    /**
     * Tests the retrieval of a customer entity by ID.
     */
    @Test
    public void testGetUserById() {
        CustomerReadResponseDto customerReadResponseDto = customerApplicationService.getCustomerById(
                CustomerReadDto.builder().customerId(CUSTOMER_ID.getValue()).build()
        );

        assertEquals("Daniel", customerReadResponseDto.getName());
    }
}
