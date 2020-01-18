package com.vskubev.business.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * @author skubev
 */
@Data
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    HttpStatus status;
    String message;
    String description;
}
