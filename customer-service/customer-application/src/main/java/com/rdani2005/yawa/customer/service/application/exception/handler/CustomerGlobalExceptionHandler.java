package com.rdani2005.yawa.customer.service.application.exception.handler;

import com.rdani2005.yawa.application.handler.ErrorDTO;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerException;
import com.rdani2005.yawa.customer.service.domain.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for handling exceptions specific to customer-related operations
 * in the application.
 */
@Slf4j
@ControllerAdvice
public class CustomerGlobalExceptionHandler {

    /**
     * Exception handler for {@link CustomerException}.
     * This method handles exceptions related to customer operations and returns an error response
     * with a HTTP status code of 400 (Bad Request).
     *
     * @param customerException The customer-specific exception that occurred.
     * @return An {@link ErrorDTO} representing the error response.
     */
    @ResponseBody
    @ExceptionHandler(value = {CustomerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(CustomerException customerException) {
        log.error(customerException.getMessage(), customerException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(customerException.getMessage())
                .build();
    }

    /**
     * Exception handler for {@link CustomerNotFoundException}.
     * This method handles exceptions when a customer is not found and returns an error response
     * with a HTTP status code of 404 (Not Found).
     *
     * @param customerNotFoundException The exception indicating that a customer was not found.
     * @return An {@link ErrorDTO} representing the error response.
     */
    @ResponseBody
    @ExceptionHandler(value = {CustomerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(CustomerNotFoundException customerNotFoundException) {
        log.error(customerNotFoundException.getMessage(), customerNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(customerNotFoundException.getMessage())
                .build();
    }
}
