package com.codecrafters.ticket_management_api.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password
) {}