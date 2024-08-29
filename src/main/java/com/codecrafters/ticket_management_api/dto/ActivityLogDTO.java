package com.codecrafters.ticket_management_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ActivityLogDTO(
        @NotNull(message = "User ID is required") UUID userId,
        @NotBlank(message = "Activity is required") String activity,
        @NotBlank(message = "IP Address is required") String ipAddress
) {}