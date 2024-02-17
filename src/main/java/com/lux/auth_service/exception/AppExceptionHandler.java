package com.lux.auth_service.exception;

import com.lux.auth_service.exception.errors.UnauthorizedException;
import com.lux.auth_service.shared.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {
    public static final String DEFAULT_INTERNAL_SERVER_ERROR_MESSAGE = ErrorMessages.DEFAULT_INTERNAL_SERVER_ERROR;
    public static final String DEFAULT_UNAUTHORIZED_ERROR_MSG = ErrorMessages.DEFAULT_UNAUTHORIZED_ERROR_MSG;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ArgumentNotValidExceptionResponse> handleInvalidArgumentsException(MethodArgumentNotValidException ex) {
        Map<String, String> errMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errMap.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(new ArgumentNotValidExceptionResponse(
                "Invalid inputs",
                errMap
        ));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return buildErrorResponse(
                Objects.nonNull(ex.getLocalizedMessage()) ? ex.getLocalizedMessage() : DEFAULT_UNAUTHORIZED_ERROR_MSG,
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<Object> internalExceptionHandler(Exception ex) {
        log.info(ex.getLocalizedMessage(), ex);

        return buildErrorResponse(
                Objects.nonNull(ex.getLocalizedMessage()) ? ex.getLocalizedMessage() : DEFAULT_INTERNAL_SERVER_ERROR_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse(null,status,message));
    }
}
