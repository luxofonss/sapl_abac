package com.lux.auth_service.shared;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ApiResponse(Object data, HttpStatus status, String message) {
}
