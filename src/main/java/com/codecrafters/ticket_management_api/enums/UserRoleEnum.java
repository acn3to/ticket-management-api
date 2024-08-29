package com.codecrafters.ticket_management_api.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRoleEnum(String role) {
        this.role = role;
    }
}