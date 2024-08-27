package com.codecrafters.ticket_management_api.dtos;

import com.codecrafters.ticket_management_api.Enum.EventCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventRecordDto (
         UUID id,
         String name,
         String description,
         String location,
         LocalDateTime startDate,
         LocalDateTime endDate,
         EventCategory category,
         UUID organizerId,
         Integer maxCapacity,
         Integer remainingCapacity,
         String status,
         BigDecimal price
){ }
