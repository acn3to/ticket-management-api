package com.codecrafters.ticket_management_api.controllers;

import com.codecrafters.ticket_management_api.dto.RatingDTO;
import com.codecrafters.ticket_management_api.models.EventModel;
import com.codecrafters.ticket_management_api.models.RatingModel;
import com.codecrafters.ticket_management_api.services.RatingService;
import com.codecrafters.ticket_management_api.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingModel>> getRatings(
            @RequestParam(required = false) EventModel eventId,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) BigDecimal maxRating,
            @RequestParam(required = false) String review,
            @RequestParam(required = false) LocalDateTime createdAfter,
            @RequestParam(required = false) LocalDateTime createdBefore) {
        try {
            List<RatingModel> ratings = ratingService.getAllRatings(
                    eventId, userId, minRating, maxRating, review, createdAfter, createdBefore);
            return ResponseEntity.ok(ratings);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable UUID id) {
        try {
            ratingService.deleteRating(id);
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<RatingModel> createRating(@RequestBody RatingDTO ratingDTO) {
        try {
            RatingModel rating = ratingService.createRating(ratingDTO);
            return ResponseEntity.status(201).body(rating);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
