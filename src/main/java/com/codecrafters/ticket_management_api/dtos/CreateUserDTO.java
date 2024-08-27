package com.codecrafters.ticket_management_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Email is required") @Email(message = "Email should be valid") String email,
        @NotNull(message = "Age is required") int age,
        @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters long") String password,
        @NotNull(message = "Role is required") String role,
        String phoneNumber,
        String address
) {}