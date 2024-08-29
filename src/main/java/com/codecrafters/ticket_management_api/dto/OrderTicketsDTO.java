package com.codecrafters.ticket_management_api.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderTicketsDTO(
        @NotNull(message = "Order ID is required") UUID orderId,
        @NotNull(message = "Ticket ID is required") UUID ticketId,
        @NotNull(message = "Quantity is required") Integer quantity
) {}