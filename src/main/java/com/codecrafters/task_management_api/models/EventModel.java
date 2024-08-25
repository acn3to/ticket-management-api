package com.codecrafters.task_management_api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
