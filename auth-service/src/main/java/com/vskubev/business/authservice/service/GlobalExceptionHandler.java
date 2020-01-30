package com.vskubev.business.authservice.service;

import com.vskubev.business.authservice.exception.CustomOAuthException;
import com.vskubev.business.authservice.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.WebUtils;

/**
 * @author skubev
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private interface Strategy<T extends Exception> {
        ErrorResponse extract(T e);
    }

    private static class ResponseStatusStrategy implements Strategy<ResponseStatusException> {
        @Override
        public ErrorResponse extract(ResponseStatusException e) {
            return ErrorResponse.builder()
                    .status(e.getStatus())
                    .message(e.getReason())
                    .description(e.getReason())
                    .build();
        }
    }

    private static class AccessDeniedStrategy implements Strategy<AccessDeniedException> {
        @Override
        public ErrorResponse extract(AccessDeniedException e) {
            return ErrorResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message(e.getMessage())
                    .description("")
                    .build();
        }
    }

    private static class UnauthorizedStrategy implements Strategy<CustomOAuthException> {
        @Override
        public ErrorResponse extract(CustomOAuthException e) {
            return ErrorResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message(e.getMessage())
                    .description("")
                    .build();
        }
    }

    private static class EmptyStrategy implements Strategy<Exception> {
        @Override
        public ErrorResponse extract(Exception e) {
            return ErrorResponse.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message("Undefined error")
                    .description("")
                    .build();
        }
    }

    @ExceptionHandler({ResponseStatusException.class,
            AccessDeniedException.class,
            CustomOAuthException.class
    })
    public final ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        ErrorResponse errorResponse;
        if (ex instanceof ResponseStatusException) {
            errorResponse = new ResponseStatusStrategy().extract((ResponseStatusException) ex);
        } else if (ex instanceof AccessDeniedException) {
            errorResponse = new AccessDeniedStrategy().extract((AccessDeniedException) ex);
        } else if (ex instanceof CustomOAuthException) {
            errorResponse = new UnauthorizedStrategy().extract((CustomOAuthException) ex);
        } else {
            errorResponse = new EmptyStrategy().extract(ex);
        }

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(errorResponse.getStatus())) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(errorResponse, headers, errorResponse.getStatus());
    }
}

