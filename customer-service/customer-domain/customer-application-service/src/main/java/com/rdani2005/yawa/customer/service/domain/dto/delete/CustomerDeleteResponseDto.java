package com.rdani2005.yawa.customer.service.domain.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO class for the response after deleting a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerDeleteResponseDto {
    private String message;
}
