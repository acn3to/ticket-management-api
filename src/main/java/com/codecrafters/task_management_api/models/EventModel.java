package com.codecrafters.task_management_api.models;

import com.codecrafters.task_management_api.Enum.EventCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events", indexes = {
        @Index(name = "idx_start_date", columnList = "start_date"),
        @Index(name = "idx_category", columnList = "category"),
        @Index(name = "idx_organizer_id", columnList = "organizer_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor


public class EventModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;


    @Enumerated(EnumType.STRING)
    public EventCategory category;

    @Column(name = "organizer_id", nullable = false)
    private UUID organizerId;

    @Column(name = "max_capacity", nullable = false)
    private int maxCapacity;

    @Column(name = "remaining_capacity", nullable = false)
    private int remainingCapacity;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Scheduled";

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}