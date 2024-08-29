package com.codecrafters.ticket_management_api.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record TicketDTO(
        @NotNull(message = "Event ID is required") UUID eventId,
        @NotNull(message = "Ticket seat information is required") Map<UUID, String> ticketSeat,
        @NotNull(message = "Batch number is required") Integer batchNumber,
        @NotNull(message = "Price is required") BigDecimal price,
        @NotNull(message = "Purchase date is required") Date purchaseDate
) {}
