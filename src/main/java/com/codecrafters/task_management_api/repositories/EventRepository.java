package com.codecrafters.task_management_api.repositories;

import com.codecrafters.task_management_api.models.EventModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventModel, UUID>, JpaSpecificationExecutor<EventModel> {
    Optional<EventModel> findEventById(UUID id);
}
