package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.enums.EventCategoryEnum;
import com.codecrafters.ticket_management_api.dto.EventDTO;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.services.EventService;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/events")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventModel>> getEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) EventCategoryEnum category,
            @RequestParam(required = false) UUID organizerId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime startTimeAfter,
            @RequestParam(required = false) LocalDateTime startTimeBefore,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer remainingCapacity,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) String location) {
        logger.info("Received request to get events with filters: name={}, category={}, organizerId={}, status={}",
                name, category, organizerId, status);
        try {
            List<EventModel> events = eventService.getAllEvents(
                    name, category, organizerId, status, startTimeAfter, startTimeBefore,
                    minPrice, maxPrice, minCapacity, remainingCapacity, createdAfter, location);
            logger.info("Returning {} events.", events.size());
            return ResponseEntity.ok(events);
        } catch (CustomException e) {
            logger.error("Custom exception occurred: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving events: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventModel> getEventById(@PathVariable UUID id) {
        logger.info("Received request to get event by ID: {}", id);
        try {
            Optional<EventModel> event = eventService.getEventById(id);
            return event.map(ResponseEntity::ok).orElseGet(() -> {
                logger.warn("Event not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            });
        } catch (CustomException e) {
            logger.error("Custom exception occurred: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving event: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody EventDTO eventDTO) {
        logger.info("Received request to create event: {}", eventDTO);
        try {
            eventService.createEvent(eventDTO);
            logger.info("Event created successfully.");
            return ResponseEntity.status(201).build();
        } catch (CustomException e) {
            logger.error("Custom exception occurred while creating event: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Error occurred while creating event: {}", e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }
}
