package com.rdani2005.yawa.customer.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * DTO class for reading customer data.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerReadDto {
    private UUID customerId;
}
