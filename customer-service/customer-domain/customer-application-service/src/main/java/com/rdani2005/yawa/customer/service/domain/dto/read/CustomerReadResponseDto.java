package com.rdani2005.yawa.customer.service.domain.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * DTO class for representing customer data in a response.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerReadResponseDto {
    private UUID id;
    private String name;
    private String lastName;
    private String identification;
    private ZonedDateTime birthDay;
    private ZonedDateTime createdAt;
}
