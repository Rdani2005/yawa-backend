package com.rdani2005.yawa.customer.service.domain.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * DTO class for the command to delete a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerDeleteCommandDto {
    private UUID customerId;
}
