package com.codecrafters.ticket_management_api.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodEnum {
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    PIX("PIX"),
    PAYPAL("PAYPAL");

    private final String paymentMethod;

    PaymentMethodEnum(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
