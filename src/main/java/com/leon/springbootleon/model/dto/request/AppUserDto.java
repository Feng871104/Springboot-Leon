package com.leon.springbootleon.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AppUserDto(
        @NotNull(message = "Username not null") String username,
        @NotNull(message = "Password not null") String password,
        @Email @NotNull(message = "Email not null") String email
) {}

