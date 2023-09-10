package com.rdani2005.yawa.accounts.service.domain.dto.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * A response DTO for the deletion of an account.
 */
@AllArgsConstructor
@Getter
@Builder
public class AccountDeleteResponse {
    @NotNull
    private final String message;
}
