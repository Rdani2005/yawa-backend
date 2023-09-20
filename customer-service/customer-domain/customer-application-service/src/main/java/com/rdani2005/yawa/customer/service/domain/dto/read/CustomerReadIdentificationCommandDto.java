package com.rdani2005.yawa.customer.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO class for reading customer data with a identification.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerReadIdentificationCommandDto {
    private String identification;
}
