package com.rdani2005.yawa.customer.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * DTO class for representing a response containing multiple customer data.
 */
@Getter
@Builder
@AllArgsConstructor
public class MultiCustomerReadResponseDto {
    private List<CustomerReadResponseDto> customers;
}
