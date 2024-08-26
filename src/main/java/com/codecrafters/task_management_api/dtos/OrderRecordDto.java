package com.codecrafters.task_management_api.dtos;

import com.codecrafters.task_management_api.Enum.EPaymentMethods;
import lombok.Data;

import java.util.UUID;


public record OrderRecordDto (
        UUID userId,
        TicketRecordDto ticketRecordDto,
        EPaymentMethods paymentMethods
){

}
