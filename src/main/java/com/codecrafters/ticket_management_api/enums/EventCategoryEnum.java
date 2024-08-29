package com.codecrafters.ticket_management_api.enums;

import lombok.Getter;

@Getter
public enum EventCategoryEnum {
    MUSIC("MUSIC"),
    THEATRE("THEATRE"),
    SPORTS("SPORTS"),
    CONFERENCE("CONFERENCE"),
    WORKSHOP("WORKSHOP"),
    FESTIVAL("FESTIVAL"),
    EXHIBITION("EXHIBITION"),
    SEMINAR("SEMINAR"),
    WEBINAR("WEBINAR"),
    COMPETITION("COMPETITION");

    private final String category;

    EventCategoryEnum(String category) {
        this.category = category;
    }
}