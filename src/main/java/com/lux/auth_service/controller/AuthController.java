package com.lux.auth_service.controller;

import com.lux.auth_service.dto.dto.LoginRequest;
import com.lux.auth_service.dto.dto.RegisterRequest;
import com.lux.auth_service.service.AuthService;
import com.lux.auth_service.shared.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(service.refreshToken(request, response));
    }
}
