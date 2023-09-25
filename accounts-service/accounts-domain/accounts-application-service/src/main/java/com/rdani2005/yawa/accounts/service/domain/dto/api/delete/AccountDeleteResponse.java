package com.rdani2005.yawa.accounts.service.domain.dto.api.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Represents a response after deleting an account.
 */
@Getter
@Builder
@AllArgsConstructor
public class AccountDeleteResponse {
    private final String message;
}
