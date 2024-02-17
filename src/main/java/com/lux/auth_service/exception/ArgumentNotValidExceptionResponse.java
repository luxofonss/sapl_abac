package com.lux.auth_service.exception;

import java.util.Map;

public record ArgumentNotValidExceptionResponse (
        String message,
        Map<String, String> errors
) {
}
