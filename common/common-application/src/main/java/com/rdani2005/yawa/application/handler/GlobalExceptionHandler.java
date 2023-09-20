package com.rdani2005.yawa.application.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling and processing various exceptions
 * that can occur in the application.
 * This class provides methods to handle general exceptions and validation
 * exceptions, including ConstraintViolationException.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles general exceptions and returns an ErrorDTO with an internal server error status.
     *
     * @param exception The exception to handle.
     * @return An ErrorDTO representing the error response.
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDTO.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected error!")
                .build();
    }

    /**
     * Handles validation exceptions, including ConstraintViolationException, and returns an ErrorDTO
     * with a bad request status.
     *
     * @param validationException The validation exception to handle.
     * @return An ErrorDTO representing the error response.
     */
    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(ValidationException validationException) {
        ErrorDTO errorDTO;
        if (validationException instanceof ConstraintViolationException) {
            String violations = extractViolationsFromException((ConstraintViolationException) validationException);
            log.error(violations, validationException);
            errorDTO = ErrorDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(violations)
                    .build();
        } else {
            String exceptionMessage = validationException.getMessage();
            log.error(exceptionMessage, validationException);
            errorDTO = ErrorDTO.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(exceptionMessage)
                    .build();
        }
        return errorDTO;
    }

    /**
     * Extracts and concatenates constraint violation messages from a ConstraintViolationException.
     *
     * @param validationException The ConstraintViolationException to extract messages from.
     * @return A concatenated string of constraint violation messages.
     */
    private String extractViolationsFromException(ConstraintViolationException validationException) {
        return validationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }
}
