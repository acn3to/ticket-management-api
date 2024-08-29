package com.codecrafters.ticket_management_api.dto;

import com.codecrafters.ticket_management_api.enums.EventCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventDTO(
        UUID id,
        @NotBlank(message = "Event name is required") String name,
        String description,
        String location,
        @NotNull(message = "Start date is required") LocalDateTime startDate,
        @NotNull(message = "End date is required") LocalDateTime endDate,
        EventCategoryEnum category,
        UUID organizerId,
        Integer maxCapacity,
        Integer remainingCapacity,
        String status,
        @NotNull(message = "Price is required") BigDecimal price
) {}
