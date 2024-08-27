package com.codecrafters.ticket_management_api.Enum;

import lombok.Getter;

@Getter
public enum EOrdersStatus {
    Pending(1),
    Completed(2),
    Cancelled(3),
    Refunded(4);

    private final Integer statusOrders;

    EOrdersStatus(Integer statusOrders){
        this.statusOrders = statusOrders;
    }

}
