package com.rdani2005.yawa.customer.service.domain.ports.input.service;

import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.create.CustomerCreateResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteCommandDto;
import com.rdani2005.yawa.customer.service.domain.dto.delete.CustomerDeleteResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;
import com.rdani2005.yawa.customer.service.domain.dto.read.MultiCustomerReadResponseDto;

public interface CustomerApplicationService {
    CustomerCreateResponseDto createCustomer(CustomerCreateCommandDto customerCreateCommandDto);
    CustomerDeleteResponseDto deleteCustomer(CustomerDeleteCommandDto customerDeleteCommandDto);
    CustomerReadResponseDto getCustomerById(CustomerReadDto customerReadDto);
    MultiCustomerReadResponseDto getAllCustomers();
}
