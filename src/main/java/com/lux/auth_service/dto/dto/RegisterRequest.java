package com.lux.auth_service.dto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Firstname is required")
    private String firstname;
    @NotNull(message = "Lastname is required")
    private String lastname;
}
