package com.rdani2005.yawa.application.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
/**
 * Data Transfer Object (DTO) representing an error response.
 * This class is typically used to encapsulate error information in the response
 * when an exception or validation error occurs in the application.
 */
@Data
@Builder
@AllArgsConstructor
public class ErrorDTO {
    private final String code;
    private final String message;
}
