package com.codecrafters.ticket_management_api.dtos;

import com.codecrafters.ticket_management_api.Enum.EPaymentMethods;

import java.util.UUID;


public record OrderRecordDto (
        UUID userId,
        TicketRecordDto ticketRecordDto,
        EPaymentMethods paymentMethods
){

}
