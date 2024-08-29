package com.codecrafters.ticket_management_api.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    REFUNDED("REFUNDED");

    private final String statusOrder;

    OrderStatusEnum(String statusOrder) {
        this.statusOrder = statusOrder;
    }
}
