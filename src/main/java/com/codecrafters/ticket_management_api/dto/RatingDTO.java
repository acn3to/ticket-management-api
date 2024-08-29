package com.codecrafters.ticket_management_api.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record RatingDTO(
        UUID id,
        @NotNull(message = "Event ID is required") UUID eventId,
        @NotNull(message = "User ID is required") UUID userId,
        @NotNull(message = "Rating is required") BigDecimal rating,
        String review
) {}
