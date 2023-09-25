package com.rdani2005.yawa.accounts.service.application.exception.handler;

import com.rdani2005.yawa.accounts.service.domain.exception.AccountNotFoundException;
import com.rdani2005.yawa.accounts.service.domain.exception.AccountsDomainException;
import com.rdani2005.yawa.application.handler.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for accounts-related exceptions.
 * This class handles exceptions of type {@link AccountsDomainException} and {@link AccountNotFoundException}
 * and returns appropriate error responses.
 */
@Slf4j
@ControllerAdvice
public class AccountGlobalExceptionHandler {

    /**
     * Handles exceptions of type {@link AccountsDomainException} and returns a BAD_REQUEST response.
     *
     * @param accountsDomainException The exception to be handled.
     * @return An {@link ErrorDTO} representing the error response.
     */
    @ResponseBody
    @ExceptionHandler(value = {AccountsDomainException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleException(AccountsDomainException accountsDomainException) {
        log.error(accountsDomainException.getMessage(), accountsDomainException);
        return ErrorDTO.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(accountsDomainException.getMessage())
                .build();
    }

    /**
     * Handles exceptions of type {@link AccountNotFoundException} and returns a NOT_FOUND response.
     *
     * @param accountNotFoundException The exception to be handled.
     * @return An {@link ErrorDTO} representing the error response.
     */
    @ResponseBody
    @ExceptionHandler(value = {AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleException(AccountNotFoundException accountNotFoundException) {
        log.error(accountNotFoundException.getMessage(), accountNotFoundException);
        return ErrorDTO.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(accountNotFoundException.getMessage())
                .build();
    }
}
