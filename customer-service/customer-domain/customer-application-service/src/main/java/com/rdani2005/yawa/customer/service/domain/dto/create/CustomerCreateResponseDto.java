package com.rdani2005.yawa.customer.service.domain.dto.create;

import com.rdani2005.yawa.customer.service.domain.dto.read.CustomerReadResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO class for the response after creating a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerCreateResponseDto {
    private CustomerReadResponseDto customer;
    private String message;
}
