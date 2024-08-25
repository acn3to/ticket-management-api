package com.codecrafters.task_management_api.Enum;

import lombok.Getter;

@Getter
public enum EPaymentMethods {
    CreditCard(1),
    DebitCard(2),
    Pix(3),
    PayPal(4);

    private final Integer paymentMethods;

    EPaymentMethods(Integer paymentMethods){
        this.paymentMethods = paymentMethods;
    }

}
