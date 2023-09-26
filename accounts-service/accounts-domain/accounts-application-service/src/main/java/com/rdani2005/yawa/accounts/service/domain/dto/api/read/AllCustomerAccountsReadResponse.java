package com.rdani2005.yawa.accounts.service.domain.dto.api.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AllCustomerAccountsReadResponse {
    private final List<MultipleAccountReadResponse> customers;
}
