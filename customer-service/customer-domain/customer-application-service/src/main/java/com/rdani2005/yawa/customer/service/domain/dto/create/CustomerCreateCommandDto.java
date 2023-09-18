package com.rdani2005.yawa.customer.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

/**
 * DTO class for creating a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerCreateCommandDto {
    private  String name;
    private  String lastName;
    private  String identification;
    private  ZonedDateTime birthDay;
}
