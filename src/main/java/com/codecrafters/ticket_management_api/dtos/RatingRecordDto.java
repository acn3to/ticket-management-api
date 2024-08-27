package com.codecrafters.ticket_management_api.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RatingRecordDto {

        private UUID id;
        private UUID eventId;
        private UUID userId;
        private BigDecimal rating;
        private String review;

        public RatingRecordDto() {}

        public RatingRecordDto(UUID id, UUID eventId, UUID userId, BigDecimal rating, String review, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.id = id;
                this.eventId = eventId;
                this.userId = userId;
                this.rating = rating;
                this.review = review;
        }
}
