package com.rdani2005.yawa.accounts.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A response DTO for the creation of an account.
 */
@AllArgsConstructor
@Getter
@Builder
public class AccountCreateResponse {
    @NotNull
    private final UUID accountId;
    @NotNull
    private final String message;
}
