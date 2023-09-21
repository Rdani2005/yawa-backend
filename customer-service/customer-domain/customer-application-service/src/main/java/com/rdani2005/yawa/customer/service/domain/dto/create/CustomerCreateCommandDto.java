package com.rdani2005.yawa.customer.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

/**
 * DTO class for creating a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class CustomerCreateCommandDto {
    @NotNull
    private  String name;
    @NotNull
    private  String lastName;
    @NotNull
    private  String identification;
    @NotNull
    private  ZonedDateTime birthDay;
}
