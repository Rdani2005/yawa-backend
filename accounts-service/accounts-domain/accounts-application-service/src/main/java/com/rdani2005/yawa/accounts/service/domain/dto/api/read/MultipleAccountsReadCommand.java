package com.rdani2005.yawa.accounts.service.domain.dto.api.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Represents a request to retrieve multiple accounts associated with a customer.
 */
@Getter
@Builder
@AllArgsConstructor
public class MultipleAccountsReadCommand {
    @NotNull
    private final UUID customerId;
}
