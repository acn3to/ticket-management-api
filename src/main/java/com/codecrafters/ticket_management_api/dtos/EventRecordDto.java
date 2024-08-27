package com.codecrafters.ticket_management_api.dtos;

import com.codecrafters.ticket_management_api.Enum.EventCategory;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventRecordDto {

        private UUID id;
        private String name;
        private String description;
        private String location;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private EventCategory category;
        private UUID organizerId;
        private int maxCapacity;
        private int remainingCapacity;
        private String status;
        private BigDecimal price;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public EventRecordDto() {}

        public EventRecordDto(UUID id, String name, String description, String location, LocalDateTime startDate, LocalDateTime endDate,
                              EventCategory category, UUID organizerId, int maxCapacity, int remainingCapacity, String status,
                              BigDecimal price, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.location = location;
                this.startDate = startDate;
                this.endDate = endDate;
                this.category = category;
                this.organizerId = organizerId;
                this.maxCapacity = maxCapacity;
                this.remainingCapacity = remainingCapacity;
                this.status = status;
                this.price = price;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }
}
