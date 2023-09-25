package com.rdani2005.yawa.accounts.service.domain.dto.api.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Represents a request to retrieve a single account by its unique identifier.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class SingleAccountReadCommand {
    @NotNull
    private final UUID accountId;
}
