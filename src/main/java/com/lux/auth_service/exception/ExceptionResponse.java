package com.lux.auth_service.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        String message,
        HttpStatus status
) {
}
