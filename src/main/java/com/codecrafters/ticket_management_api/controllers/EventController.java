package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.enums.EventCategoryEnum;
import com.codecrafters.ticket_management_api.dto.EventDTO;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.services.EventService;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
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
        try {
            List<EventModel> events = eventService.getAllEvents(
                    name, category, organizerId, status, startTimeAfter, startTimeBefore,
                    minPrice, maxPrice, minCapacity, remainingCapacity, createdAfter, location);
            return ResponseEntity.ok(events);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventModel> getEventById(@PathVariable UUID id) {
        try {
            Optional<EventModel> event = eventService.getEventById(id);
            return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            eventService.createEvent(eventDTO);
            return ResponseEntity.status(201).build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
