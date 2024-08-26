package com.codecrafters.task_management_api.dtos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record TicketRecordDto(
        UUID eventId,
        Map<UUID,String> ticketSeat,
        UUID ticketManagerId,
        Integer batchNumber,
        BigDecimal price,
        Date purchaseDate
) {
}

