package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.enums.EventCategoryEnum;
import com.codecrafters.ticket_management_api.dto.EventDTO;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.repositories.EventRepository;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import com.codecrafters.ticket_management_api.specs.EventSpecifications;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import jakarta.transaction.Transactional;
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

    @Autowired
    private UserRepository userRepository;

    public List<EventModel> getAllEvents(
            String name,
            EventCategoryEnum category,
            UUID organizerId,
            String status,
            LocalDateTime startDateAfter,
            LocalDateTime startDateBefore,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minCapacity,
            Integer remainingCapacity,
            LocalDateTime createdAfter,
            String location) {
        try {
            Specification<EventModel> spec = Specification
                    .where(EventSpecifications.hasName(name))
                    .and(EventSpecifications.hasCategory(category))
                    .and(EventSpecifications.hasOrganizerId(organizerId))
                    .and(EventSpecifications.hasStatus(status))
                    .and(EventSpecifications.hasStartDateBetween(startDateAfter, startDateBefore))
                    .and(EventSpecifications.hasPriceBetween(minPrice, maxPrice))
                    .and(EventSpecifications.hasMaxCapacityGreaterThanOrEqual(minCapacity))
                    .and(EventSpecifications.hasRemainingCapacityGreaterThanOrEqual(remainingCapacity))
                    .and(EventSpecifications.isCreatedAfter(createdAfter))
                    .and(EventSpecifications.hasLocation(location));

            return eventRepository.findAll(spec);
        } catch (Exception e) {
            throw new CustomException("Error retrieving events: " + e.getMessage());
        }
    }

    @Transactional
    public void createEvent(EventDTO data) {
        try {
            if (data == null) {
                throw new CustomException("EventDTO cannot be null");
            }

            var organizer = userRepository.findById(data.organizerId())
                    .orElseThrow(() -> new CustomException("Organizer not found"));

            EventModel newEvent = EventModel.builder()
                    .id(data.id())
                    .name(data.name())
                    .description(data.description())
                    .category(data.category())
                    .location(data.location())
                    .maxCapacity(data.maxCapacity())
                    .startDate(data.startDate())
                    .endDate(data.endDate())
                    .organizer(organizer)
                    .remainingCapacity(data.maxCapacity())
                    .status(data.status())
                    .price(data.price())
                    .build();

            eventRepository.save(newEvent);
        } catch (Exception e) {
            throw new CustomException("Error creating event: " + e.getMessage());
        }
    }

    public Optional<EventModel> getEventById(UUID id) {
        try {
            return eventRepository.findEventById(id)
                    .or(() -> {
                        throw new CustomException("Event not found with ID: " + id);
                    });
        } catch (Exception e) {
            throw new CustomException("Error retrieving event: " + e.getMessage());
        }
    }

    public List<EventModel> getEventsByOrganizer(UUID organizerId) {
        try {
            return eventRepository.findByOrganizerId(organizerId);
        } catch (Exception e) {
            throw new CustomException("Error retrieving events by organizer: " + e.getMessage());
        }
    }

    public List<EventModel> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        try {
            return eventRepository.findEventsByDateRange(startDate, endDate);
        } catch (Exception e) {
            throw new CustomException("Error retrieving events by date range: " + e.getMessage());
        }
    }
}
