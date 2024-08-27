package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.Enum.EventCategory;
import com.codecrafters.ticket_management_api.dtos.EventRecordDto;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.services.EventService;
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
    public List<EventModel> getEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) EventCategory category,
            @RequestParam(required = false) UUID organizerId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime startTimeAfter,
            @RequestParam(required = false) LocalDateTime startTimeBefore,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer remainingCapacity,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) String location
    ) {
        return eventService.getAllEvents(
                name, category, organizerId, status, startTimeAfter, startTimeBefore,
                minPrice, maxPrice, minCapacity, remainingCapacity, createdAfter, location
        );
    }

    // Solicitação do Coojak
    @GetMapping("/{id}")
    public ResponseEntity<EventModel> getEventById(@PathVariable UUID id) {
        Optional<EventModel> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventModel>  createEvent(@RequestBody EventRecordDto eventRecordDto){
        eventService.createEvent(eventRecordDto);
        return ResponseEntity.ok().build();
    }
}
