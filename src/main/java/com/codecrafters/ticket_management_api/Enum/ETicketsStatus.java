package com.codecrafters.ticket_management_api.Enum;

import lombok.Getter;

@Getter
public enum ETicketsStatus {

    Available (1),
    Reserved(2),
    Sold(3),
    Cancelled(4);

    private final Integer statusTicket;

    ETicketsStatus(Integer statusTicket){
        this.statusTicket = statusTicket;
    }

}
