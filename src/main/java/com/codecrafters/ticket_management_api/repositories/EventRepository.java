package com.codecrafters.ticket_management_api.repositories;

import com.codecrafters.ticket_management_api.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventModel, UUID>, JpaSpecificationExecutor<EventModel> {
    Optional<EventModel> findEventById(UUID id);

    List<EventModel> findByOrganizerId(UUID organizerId);

    @Query("SELECT e FROM EventModel e WHERE e.startDate BETWEEN :startDate AND :endDate")
    List<EventModel> findEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}