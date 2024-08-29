package com.codecrafters.ticket_management_api.services;

import com.codecrafters.ticket_management_api.enums.EventCategoryEnum;
import com.codecrafters.ticket_management_api.dto.EventDTO;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.repositories.EventRepository;
import com.codecrafters.ticket_management_api.repositories.UserRepository;
import com.codecrafters.ticket_management_api.specs.EventSpecifications;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

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
        logger.info("Fetching all events with filters: name={}, category={}, organizerId={}, status={}", name, category,
                organizerId, status);
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

            List<EventModel> events = eventRepository.findAll(spec);
            logger.info("Retrieved {} events.", events.size());
            return events;
        } catch (Exception e) {
            logger.error("Error retrieving events: {}", e.getMessage());
            throw new CustomException("Error retrieving events: " + e.getMessage());
        }
    }

    @Transactional
    public void createEvent(EventDTO data) {
        logger.info("Creating event with data: {}", data);
        try {
            if (data == null) {
                throw new CustomException("EventDTO cannot be null");
            }

            var organizer = userRepository.findById(data.organizerId())
                    .orElseThrow(() -> new CustomException("Organizer not found"));
            logger.info("Organizer found: {}", organizer);

            EventModel newEvent = EventModel.builder()
                    .id(UUID.randomUUID())
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
            logger.info("Event created successfully: {}", newEvent);
        } catch (Exception e) {
            logger.error("Error creating event: {}", e.getMessage());
            throw new CustomException("Error creating event: " + e.getMessage());
        }
    }

    public Optional<EventModel> getEventById(UUID id) {
        logger.info("Fetching event with ID: {}", id);
        try {
            return eventRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error retrieving event by ID: {} - {}", id, e.getMessage());
            throw new CustomException("Error retrieving event by ID: " + e.getMessage());
        }
    }

    public List<EventModel> getEventsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        logger.info("Fetching events between dates: {} and {}", startDate, endDate);
        try {
            return eventRepository.findEventsByDateRange(startDate, endDate);
        } catch (Exception e) {
            logger.error("Error retrieving events by date range: {}", e.getMessage());
            throw new CustomException("Error retrieving events by date range: " + e.getMessage());
        }
    }
}
