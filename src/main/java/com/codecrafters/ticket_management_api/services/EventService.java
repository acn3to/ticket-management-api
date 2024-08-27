package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.Enum.EventCategory;
import com.codecrafters.ticket_management_api.dtos.EventRecordDto;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.repositories.EventRepository;
import com.codecrafters.ticket_management_api.specifications.EventSpecification;
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

    public EventModel createEvent(EventRecordDto eventRecordDto) {
        if (eventRecordDto == null) {
            throw new IllegalStateException("Event Record Dto is null");
        }
        EventModel eventModel = new EventModel();
        eventModel.setId(eventRecordDto.getId());
        eventModel.setName(eventRecordDto.getName());
        eventModel.setDescription(eventRecordDto.getDescription());
        eventModel.setCategory(eventRecordDto.getCategory());
        eventModel.setLocation(eventRecordDto.getLocation());
        eventModel.setMaxCapacity(eventRecordDto.getMaxCapacity());
        eventModel.setStartDate(eventRecordDto.getStartDate());
        eventModel.setEndDate(eventRecordDto.getEndDate());
        eventModel.setOrganizerId(eventRecordDto.getOrganizerId());
        eventModel.setRemainingCapacity(eventModel.getMaxCapacity());
        eventModel.setStatus(eventRecordDto.getStatus());

        eventRepository.save(eventModel);
        return eventModel;
    }

}


