package com.codecrafters.ticket_management_api.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RatingRecordDto(
         UUID id,
         UUID eventId,
         UUID userId,
         BigDecimal rating,
         String review
) {
}
