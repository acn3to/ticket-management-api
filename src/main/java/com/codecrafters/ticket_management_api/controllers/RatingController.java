package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dtos.RatingRecordDto;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.models.RatingModel;
import com.codecrafters.ticket_management_api.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public List<RatingModel> getRatings(
            @RequestParam(required = false) EventModel eventId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) BigDecimal maxRating,
            @RequestParam(required = false) String review,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) LocalDateTime createdBefore
    ) {
        return ratingService.getAllRatings(
                eventId, userId, minRating, maxRating, review, createdAfter, createdBefore
        );
    }

    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable UUID id) {
        ratingService.deleteRating(id);
    }

    @PostMapping
    public RatingModel publishRating(@RequestBody RatingRecordDto ratingRecordDto) {
        return ratingService.publishRating(ratingRecordDto);
    }
}
