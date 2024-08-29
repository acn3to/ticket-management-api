package com.codecrafters.ticket_management_api.enums;

import lombok.Getter;

@Getter
public enum TicketStatusEnum {

    AVAILABLE("AVAILABLE"),
    SOLD("SOLD"),
    CANCELLED("CANCELLED");

    private final String statusTicket;

    TicketStatusEnum(String statusTicket) {
        this.statusTicket = statusTicket;
    }
}
