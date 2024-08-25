package com.codecrafters.task_management_api.services;

import com.codecrafters.task_management_api.models.EventCategory;
import com.codecrafters.task_management_api.models.EventModel;
import com.codecrafters.task_management_api.repositories.EventRepository;
import com.codecrafters.task_management_api.specifications.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventModel> getAllEvents(
            String name,
            EventCategory category,
            UUID organizerId,
            String status,
            LocalDateTime startDateAfter,
            LocalDateTime startDateBefore,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minCapacity,
            Integer remainingCapacity,
            LocalDateTime createdAfter,
            String location
    ) {
        Specification<EventModel> spec = Specification
                .where(EventSpecification.hasName(name))
                .and(EventSpecification.hasCategory(category))
                .and(EventSpecification.hasOrganizerId(organizerId))
                .and(EventSpecification.hasStatus(status))
                .and(EventSpecification.hasStartDateBetween(startDateAfter, startDateBefore))
                .and(EventSpecification.hasPriceBetween(minPrice, maxPrice))
                .and(EventSpecification.hasMaxCapacityGreaterThanOrEqual(minCapacity))
                .and(EventSpecification.hasRemainingCapacityGreaterThanOrEqual(remainingCapacity))
                .and(EventSpecification.isCreatedAfter(createdAfter))
                .and(EventSpecification.hasLocation(location));

        return eventRepository.findAll(spec);
    }

    // Solicitação do Coojak
    public Optional<EventModel> getEventById(UUID id) {
        return eventRepository.findEventById(id);

    }
}


