package com.lux.auth_service.service;

import com.lux.auth_service.config.JWTService;
import com.lux.auth_service.dto.dto.AuthResponse;
import com.lux.auth_service.dto.dto.LoginRequest;
import com.lux.auth_service.dto.dto.RegisterRequest;
import com.lux.auth_service.enums.Role;
import com.lux.auth_service.exception.errors.UnauthorizedException;
import com.lux.auth_service.model.User;
import com.lux.auth_service.repository.UserRepo;
import com.lux.auth_service.shared.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo repo;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse register(RegisterRequest request) {
    var user = User.builder().firstname(request.getFirstname())
            .lastname(request.getLastname())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    repo.save(user);
    var jwtToken  = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);

    AuthResponse authData = AuthResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();

    return ApiResponse.<AuthResponse>builder()
            .status(HttpStatus.CREATED)
            .message("User registered successfully")
            .data(authData)
            .build();
    }

    public ApiResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            var user = repo.findByUsername(request.getUsername()).orElseThrow();
            var jwtToken  = jwtService.generateAccessToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            AuthResponse data = AuthResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
            return ApiResponse.<AuthResponse>builder()
                    .status(HttpStatus.OK)
                    .message("User authenticated successfully")
                    .data(data)
                    .build();
        } catch (Exception e) {
            throw new UnauthorizedException(e.getLocalizedMessage());
        }
    }

    public ApiResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        final Cookie[] cookies = request.getCookies();
        AtomicReference<String> jwtRefreshToken = new AtomicReference<>();
        final String username;

        if (cookies == null) {
            throw new UnauthorizedException("Refresh token not found");
        }

        Arrays.stream(cookies).forEach(cookie -> {
            if (cookie.getName().equals("refreshToken")) {
                jwtRefreshToken.set(cookie.getValue());
            }
        });

        if (jwtRefreshToken.get() == null) {
            throw new UnauthorizedException("Refresh token not found");
        }
        username = jwtService.extractUsername(jwtRefreshToken.get());

        if (username != null) {
            UserDetails userDetails = this.repo.findByUsername(username).orElseThrow();

            if (jwtService.isTokenValid(jwtRefreshToken.get(), userDetails)) {
                var jwtToken = jwtService.generateAccessToken(userDetails);
                var refreshToken = jwtRefreshToken.get();

                AuthResponse data= AuthResponse.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                        .build();
                return ApiResponse.<AuthResponse>builder()
                        .status(HttpStatus.OK)
                        .message("Token refreshed successfully")
                        .data(data)
                        .build();
            }
        }
        throw new UnauthorizedException("Refresh token not found");
    }
}
