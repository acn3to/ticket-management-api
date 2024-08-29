package com.codecrafters.ticket_management_api.dto;

import com.codecrafters.ticket_management_api.enums.PaymentMethodEnum;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderDTO(
        @NotNull(message = "User ID is required") UUID userId,
        @NotNull(message = "Ticket ID is required") UUID ticketId,
        @NotNull(message = "Total amount is required") BigDecimal totalAmount,
        @NotNull(message = "Payment method is required") PaymentMethodEnum paymentMethod
) {}
